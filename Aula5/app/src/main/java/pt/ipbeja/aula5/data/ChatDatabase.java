package pt.ipbeja.aula5.data;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Contact.class, Message.class}, version = 1, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public static ChatDatabase getInstance(Context context) {
        context = context.getApplicationContext();
        if(instance == null) {
            instance = Room.databaseBuilder(context, ChatDatabase.class, "chat_db")
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


    public abstract ContactDao contactDao();

    public abstract MessageDao messageDao();
}
