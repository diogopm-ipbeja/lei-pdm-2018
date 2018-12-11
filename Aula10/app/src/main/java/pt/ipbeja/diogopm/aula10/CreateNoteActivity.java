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

            this.thumbnailBytes = ImageUtils.getBytesFromBitmap(imageBitmap);

            notePhoto.setImageBitmap(imageBitmap);
        }
    }


    public void onSaveNoteClick(View view) {
        // todo validate field (including photo) ...

        Note note = new Note(
                noteTitleEditText.getText().toString(),
                noteDescriptionEditText.getText().toString(),
                thumbnailBytes);

        new SaveNoteTask().execute(note);

    }


    private void uploadNote(Note note) {
        // todo save note to Firebase-Firestore and photo bytes to Firebase-Storage (called on SaveNoteTask#onPostExecute)
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
            // todo upload note to Firebase-Firestore and image to Firebase-Storage
            uploadNote(note);
        }
    }



}
