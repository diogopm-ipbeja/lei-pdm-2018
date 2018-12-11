package pt.ipbeja.diogopm.aula10.data;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class NoteDao implements BaseDao<Note> {

    @Query("select * from notes")
    public abstract LiveData<List<Note>> getNotes();

    @Query("select * from notes where id = :id")
    public abstract LiveData<Note> getNote(long id);

}
