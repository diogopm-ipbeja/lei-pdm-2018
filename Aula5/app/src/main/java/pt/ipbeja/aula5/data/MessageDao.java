package pt.ipbeja.aula5.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface MessageDao {

    @Insert
    long insert(Message message);

    @Query("select * from messages where contactId = :contactId")
    List<Message> getMessagesForContact(long contactId);


}
