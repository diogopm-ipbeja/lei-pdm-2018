package pt.ipbeja.diogopm.aula10;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.List;

import pt.ipbeja.diogopm.aula10.data.Note;

public class MainActivity extends AppCompatActivity {

    private RecyclerView noteList;
    private NoteAdapter adapter;

    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.progressBar = findViewById(R.id.save_progress);
        this.noteList = findViewById(R.id.note_list);
        this.noteList.setLayoutManager(new LinearLayoutManager(this));
        this.adapter = new NoteAdapter();
        this.noteList.setAdapter(adapter);

        // TODO start observing the 'notes' table and notify the adapter when it emits data

    }

    //  --- Lifecycle callbacks for demo purposes --- //

    @Override
    protected void onStart() {
        super.onStart();
        System.out.println("onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        System.out.println("onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        System.out.println("onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        System.out.println("onStop");
    }

    //  ---------------------------------------------- //

    public void onAddNoteClick(View view) {

        new AlertDialog.Builder(this)
                .setTitle("Add note")
                .setView(R.layout.note_dialog)
                .setPositiveButton("Add note", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {



                        AlertDialog d = (AlertDialog) dialog;
                        EditText title = d.findViewById(R.id.note_title);
                        EditText description = d.findViewById(R.id.note_description);

                        // TODO save new note to DB asynchronously (Thread or AsyncTask)

                    }
                })
                .setNegativeButton("Cancel", null)
                .show();

    }

    private static class NoteViewHolder extends RecyclerView.ViewHolder {

        private Note note;

        TextView title;
        TextView description;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
        }

        void bind(Note note) {
            this.note = note;
            this.title.setText(note.getTitle());
            this.description.setText(note.getNote());
        }
    }


    private class NoteAdapter extends RecyclerView.Adapter<NoteViewHolder> {

        private List<Note> data;

        public void setData(List<Note> notes) {
            this.data = notes;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.note_item, viewGroup, false);
            return new NoteViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull NoteViewHolder noteViewHolder, int i) {
            Note note = data.get(i);
            noteViewHolder.bind(note);
        }

        @Override
        public int getItemCount() {
            return data == null ? 0 : data.size();
        }
    }

    // TODO add a new inner class 'SaveNewNoteTask' that extends AsyncTask

}
