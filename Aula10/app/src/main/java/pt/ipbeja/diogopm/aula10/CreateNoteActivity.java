package pt.ipbeja.diogopm.aula10;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.UploadTask;

import java.io.File;

import pt.ipbeja.diogopm.aula10.data.Note;
import pt.ipbeja.diogopm.aula10.data.NoteDatabase;
import pt.ipbeja.diogopm.aula10.utils.ImageUtils;

public class CreateNoteActivity extends AppCompatActivity {

    private static final int IMAGE_CAPTURE_REQUEST_CODE = 1;

    private EditText noteTitleEditText;
    private EditText noteDescriptionEditText;
    private ImageView notePhoto;

    private byte[] thumbnailBytes;

    public static void start(Context context) {
        Intent starter = new Intent(context, CreateNoteActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);

        this.noteTitleEditText = findViewById(R.id.note_title);
        this.noteDescriptionEditText = findViewById(R.id.note_description);
        this.notePhoto = findViewById(R.id.note_picture);
    }

    public void onTakePictureClick(View view) {
        takePicture();
    }



    private void takePicture() {

            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(takePictureIntent, IMAGE_CAPTURE_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == IMAGE_CAPTURE_REQUEST_CODE && resultCode == RESULT_OK) {


            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            notePhoto.setImageBitmap(imageBitmap);

            // podemos guardar já os bytes do thumbnail para depois colocar na Firebase Storage
            this.thumbnailBytes = ImageUtils.getBytesFromBitmap(imageBitmap);

            // todo fetch the actual photo file (not just the thumbnail)
            // todo consider saving the thumbnail bytes on the Firestore document (for a quick preview while the actual photo is being downloaded)
        }
    }


    public void onSaveNoteClick(View view) {
        // todo validate fields (including photo) ...

        Note note = new Note(
                noteTitleEditText.getText().toString(),
                noteDescriptionEditText.getText().toString(),
                thumbnailBytes);

        // Lançamos uma AsyncTask para guardar os dados na BD
        new SaveNoteTask().execute(note);

    }


    private void uploadNote(Note note) {

        // Colocamos os dados da Note (excepto os bytes do thumbnail - ver @Exclude na class Note) na Firestore
        // 1.
        FirebaseFirestore.getInstance()
                .collection("notes")
                .add(note)
                .addOnSuccessListener(this, new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference ref) {
                        // (*)
                    }
                });

        // E os bytes na Storage
        // 2.
        FirebaseStorage.getInstance()
                .getReference()
                .child(note.getId() + ".jpg")
                .putBytes(thumbnailBytes) // Podemos também colocar um File (#putFile)
                .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onProgress(UploadTask.TaskSnapshot snap) {
                        int progress = (int) ((snap.getBytesTransferred() / snap.getTotalByteCount()) * 100);
                        System.out.println("Progress -> " + progress);
                    }
                })
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        finish(); // Quando o upload terminar, podemos terminar a Activity
                    }
                });


        // Atenção que as chamadas acima são assíncronas. Apesar de altamente improvável,
        // pode acontecer que o upload dos bytes termine antes do upload da Note (1.)
        // Para contornar isto, podiamos invocar o método de enviar os bytes (2.) dentro do OnSuccessListener
        // do método de envio da Note (*)

    }

    private class SaveNoteTask extends AsyncTask<Note, Void, Note> {

        @Override
        protected Note doInBackground(Note... notes) {
            Note note = notes[0];
            long id = NoteDatabase.getInstance(getApplicationContext())
                    .noteDao()
                    .insert(note);

            note.setId(id);

            return note;
        }

        @Override
        protected void onPostExecute(Note note) {
            // Depois da nota ser persistida na BD, vamos colocar também estes dados na Firebase
            uploadNote(note);
        }
    }



}
