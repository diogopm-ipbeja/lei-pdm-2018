package pt.ipbeja.aula5.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface ContactDao {

    @Insert
    long insert(Contact contact);

    @Query("select * from contacts")
    List<Contact> getAllContacts();

    @Delete
    int delete(Contact contact);

    @Query("delete from contacts")
    int deleteAll();

    @Query(("select * from contacts where id = :contactId"))
    Contact getContact(long contactId);
}
