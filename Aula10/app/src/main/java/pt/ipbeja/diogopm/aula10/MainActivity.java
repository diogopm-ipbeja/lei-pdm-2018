package pt.ipbeja.diogopm.aula10;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import javax.annotation.Nullable;

import pt.ipbeja.diogopm.aula10.data.Note;
import pt.ipbeja.diogopm.aula10.data.NoteDatabase;

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


        // Começamos a observar alterações na tabela das Notes
        NoteDatabase.getInstance(getApplicationContext())
                .noteDao()
                .getNotes()
                .observe(this, new Observer<List<Note>>() {
                    @Override
                    public void onChanged(@android.support.annotation.Nullable List<Note> notes) {
                        adapter.setData(notes);
                    }
                });

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

        CreateNoteActivity.start(this);
    }

    private class NoteViewHolder extends RecyclerView.ViewHolder {

        private Note note;

        TextView title;
        TextView description;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.note_title);
            description = itemView.findViewById(R.id.note_description);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ViewNoteActivity.start(MainActivity.this, note.getId());
                }
            });
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

}
