package pt.ipbeja.diogopm.aula10;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import pt.ipbeja.diogopm.aula10.data.Note;
import pt.ipbeja.diogopm.aula10.data.NoteDatabase;
import pt.ipbeja.diogopm.aula10.utils.ImageUtils;

public class ViewNoteActivity extends AppCompatActivity {

    public static final String NOTE_ID = "noteId";

    private TextView noteTitle;
    private TextView noteDescription;
    private ImageView notePhoto;
    private ProgressBar progressBar;

    private long noteId;
    private Note currentNote;

    public static void start(Context context, long id) {
        Intent starter = new Intent(context, ViewNoteActivity.class);
        starter.putExtra(NOTE_ID, id);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        long noteId = getIntent().getLongExtra(NOTE_ID, 0);

        // Check if we have a valid id before doing anything
        if(noteId == 0) {
            finish();
            return;
        }

        this.noteTitle = findViewById(R.id.note_title);
        this.noteDescription = findViewById(R.id.note_description);
        this.notePhoto = findViewById(R.id.note_picture);
        this.progressBar = findViewById(R.id.loading);


        // Start observing the Note
        NoteDatabase.getInstance(getApplicationContext())
                .noteDao()
                .getNote(noteId)
                .observe(this, new Observer<Note>() {
                    @Override
                    public void onChanged(@Nullable Note note) {

                        if(note != null) {
                            currentNote = note;
                            loadNote();
                        }
                    }

                });

    }


    /**
     * Loads Note contents to the Views
     */
    private void loadNote() {
        noteTitle.setText(currentNote.getTitle());
        noteDescription.setText(currentNote.getNote());

        if(currentNote.getPhotoBytes() != null) {
            setPhoto();
        }
        else {
            progressBar.setVisibility(View.VISIBLE);

            // todo fetch image from Firebase-Storage and update the db record (photoBytes)

        }

    }

    private void setPhoto() {
        Bitmap bitmap = ImageUtils.getBitmapFromBytes(currentNote.getPhotoBytes());
        notePhoto.setImageBitmap(bitmap);
    }


    private class UpdateNoteTask extends AsyncTask<Note, Void, Void> {

        @Override
        protected Void doInBackground(Note... notes) {
            NoteDatabase.getInstance(getApplicationContext())
                    .noteDao()
                    .update(notes[0]);
            return null;
        }
    }
}
