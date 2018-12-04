package pt.ipbeja.diogopm.aula10.data;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public abstract class NoteDao implements BaseDao<Note> {

    @Query("select * from notes")
    public abstract List<Note> getNotes();

}
