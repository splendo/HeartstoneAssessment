package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = Mechanic.Scheme.NAME)
public class Mechanic {

    public interface Scheme {
        String NAME = "mechanics";

        interface Properties {
            String ID = "id";
            String NAME = "name";
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    @NonNull
    private String id;
    @ColumnInfo(name = Scheme.Properties.NAME)
    @NonNull
    private String name;

    public Mechanic(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mechanic)) return false;

        Mechanic mechanic = (Mechanic) o;

        if (!id.equals(mechanic.id)) return false;
        return name.equals(mechanic.name);
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "Mechanic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
