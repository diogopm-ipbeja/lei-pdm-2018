package pt.ipbeja.diogopm.aula10;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreSettings;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

import pt.ipbeja.diogopm.aula10.data.Note;
import pt.ipbeja.diogopm.aula10.data.NoteDatabase;

public class NotesApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Este é o ponto de entrada na aplicação
        // Sempre que a aplicação é iniciada, passa por este método
        // É a altura ideal para fazer configurações na nossa app
        // Ver atributo "name" do nó "Application" do AndroidManifest.xml (obrigatório)

        // Impedir que se faça cache dos dados da Firestore
        FirebaseFirestore instance = FirebaseFirestore.getInstance();
        FirebaseFirestoreSettings settings = new FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(false)
                .build();
        instance.setFirestoreSettings(settings);


        // Na primeira vez que a aplicação corre...
        final SharedPreferences prefs = getSharedPreferences("app_prefs", Context.MODE_PRIVATE);
        boolean firstTime = prefs.getBoolean("firstTime", true);

        if(firstTime) {
            // ... vamos buscar os dados que estão na Firestore...
            FirebaseFirestore.getInstance()
                    .collection("notes")
                    .get()
                    .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                        @Override
                        public void onSuccess(QuerySnapshot snap) {

                            prefs.edit().putBoolean("firstTime", false).apply();

                            final List<Note> notes = snap.toObjects(Note.class);

                            new Thread() {
                                @Override
                                public void run() {
                                    // ... e guarda-los na BD local
                                    NoteDatabase.getInstance(getApplicationContext())
                                            .noteDao()
                                            .insert(notes);
                                }
                            }.start();


                        }
                    });

        }


    }
}
