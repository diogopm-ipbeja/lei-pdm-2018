package pt.ipbeja.aula5.data;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Relation;

import java.util.List;

@Entity(tableName = "contacts")
public class Contact {

    @PrimaryKey(autoGenerate = true)
    private long id;

    private String name;


    public Contact(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getInitials() {
        String[] initials = name.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(initials[0].charAt(0));
        if(initials.length > 1) {
            sb.append(initials[initials.length-1].charAt(0));
        }
        return sb.toString();
    }

}
