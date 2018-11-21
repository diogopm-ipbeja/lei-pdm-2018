package pt.ipbeja.aula5.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "contacts")
public class Contact {


    @PrimaryKey(autoGenerate = true)
    private long id;
    private String name;

    @Embedded
    private Coordinates coordinates;

    @Ignore
    private String initials;

    public Contact(long id, String name, Coordinates coordinates) {
        this.id = id;
        this.name = name;
        this.coordinates = coordinates;
        setInitials();
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
        setInitials();
    }

    public Coordinates getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    private void setInitials() {
        String[] names = name.split(" ");
        StringBuilder sb = new StringBuilder();
        sb.append(names[0].charAt(0));
        if(names.length > 1) {
            sb.append(names[names.length-1].charAt(0));
        }
        this.initials = sb.toString().toUpperCase();
    }

    public String getInitials() {
        return this.initials;
    }

}
