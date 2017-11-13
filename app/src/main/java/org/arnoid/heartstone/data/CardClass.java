package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.arnoid.heartstone.data.util.CardsFilter;

/**
 * Entity to represent card class.
 */
@Entity(tableName = CardClass.Scheme.NAME,
        indices = {@Index(value = CardClass.Scheme.Properties.NAME, unique = true)})
public class CardClass implements CardsFilter.Filterable {

    public static final String NONE = "none";

    public interface Scheme {
        String NAME = "classes";

        interface Queries {
            String ALL = "select * from " + CardClass.Scheme.NAME;
            String ALL_FILER_BY_NAME = "select * from " + CardClass.Scheme.NAME + " WHERE " + CardClass.Scheme.Properties.NAME + " IN (:names)";
        }

        interface Properties {
            String ID = "id";
            String NAME = "name";
        }
    }

    public CardClass(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    private long id;
    @ColumnInfo(name = Scheme.Properties.NAME)
    @NonNull
    private String name;
    @Ignore
    private boolean checked;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardClass)) return false;

        CardClass cardClass = (CardClass) o;

        if (id != cardClass.id) return false;
        return name.equals(cardClass.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CardClass{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}