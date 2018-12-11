package pt.ipbeja.diogopm.aula10.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Note.class}, version = 2, exportSchema = false)
public abstract class NoteDatabase extends RoomDatabase {

    private static NoteDatabase instance;

    public synchronized static NoteDatabase getInstance(Context context) {
        if(context != context.getApplicationContext()) throw new IllegalArgumentException("Must be Application context!");

        if(instance == null) {
            instance = Room.databaseBuilder(context, NoteDatabase.class, "notes_db")
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return instance;
    }

    public abstract NoteDao noteDao();

}
