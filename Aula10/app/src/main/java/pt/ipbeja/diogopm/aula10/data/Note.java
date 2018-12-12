package pt.ipbeja.diogopm.aula10.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import com.google.firebase.firestore.Exclude;

@Entity(tableName = "notes")
public class Note {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String title;

    private String note;

    @ColumnInfo(typeAffinity = ColumnInfo.BLOB)
    private byte[] photoBytes;

    @Ignore
    public Note() {
    }

    public Note(long id, String title, String note, byte[] photoBytes) {
        this.id = id;
        this.title = title;
        this.note = note;
        this.photoBytes = photoBytes;
    }

    @Ignore
    public Note(String title, String note, byte[] photoBytes) {
        this(0, title, note, photoBytes);
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

    @Exclude // Para excluir o atributo na Firestore (esta annotation só funciona nos campos se estes forem public)
    public byte[] getPhotoBytes() {
        return photoBytes;
    }

    public void setPhotoBytes(byte[] photoBytes) {
        this.photoBytes = photoBytes;
    }
}
