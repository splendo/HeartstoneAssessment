package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.arnoid.heartstone.data.util.CardsFilter;

/**
 * Entity to represent card mechanic.
 */
@Entity(tableName = CardMechanic.Scheme.NAME,
        indices = {@Index(value = CardMechanic.Scheme.Properties.NAME, unique = true)})
public class CardMechanic implements CardsFilter.Filterable {

    public static final String NONE = "none";

    public interface Scheme {
        String NAME = "mechanics";

        interface Queries {
            String ALL = "select * from " + Scheme.NAME;
            String ALL_FILER_BY_NAME = "select * from " + Scheme.NAME + " WHERE " + Properties.NAME + " IN (:names)";
        }

        interface Properties {
            String ID = "id";
            String NAME = "name";
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    @NonNull
    private long id;
    @ColumnInfo(name = Scheme.Properties.NAME)
    @NonNull
    private String name;
    @Ignore
    private boolean checked;

    public CardMechanic(@NonNull String name) {
        this.name = name;
    }

    @NonNull
    public long getId() {
        return id;
    }

    public void setId(@NonNull long id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
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
        if (!(o instanceof CardMechanic)) return false;

        CardMechanic that = (CardMechanic) o;

        if (id != that.id) return false;
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CardMechanic{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
