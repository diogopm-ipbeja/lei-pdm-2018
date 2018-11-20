package pt.ipbeja.aula5.data;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.migration.Migration;
import android.content.Context;
import android.support.annotation.NonNull;

@Database(entities = {Contact.class, Message.class}, version = 2, exportSchema = false)
public abstract class ChatDatabase extends RoomDatabase {

    private static ChatDatabase instance;

    public static ChatDatabase getInstance(Context context) {
        context = context.getApplicationContext();
        if(instance == null) {
            instance = Room.databaseBuilder(context, ChatDatabase.class, "chat_db")
                    .addMigrations(MIGRATION_1_2)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }

        return instance;
    }


    public abstract ContactDao contactDao();

    public abstract MessageDao messageDao();

    // ----- MIGRATIONS ----- //

    private static final Migration MIGRATION_1_2 = new Migration(1,2) {
        @Override
        public void migrate(@NonNull SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE contacts ADD latitude REAL default 1000;");
            database.execSQL("ALTER TABLE contacts ADD longitude REAL default 1000;");
        }
    };
}
