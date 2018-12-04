package pt.ipbeja.diogopm.aula10.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey
    private long id;

    private String title;
    private String note;

    public Note(long id, String title, String note) {
        this.id = id;
        this.title = title;
        this.note = note;
    }

    @Ignore
    public Note(String title, String note) {
        this.title = title;
        this.note = note;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
