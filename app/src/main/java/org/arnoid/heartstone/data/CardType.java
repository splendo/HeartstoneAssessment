package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.arnoid.heartstone.data.util.CardsFilter;

/**
 * Entity to represent card type.
 */
@Entity(tableName = CardType.Scheme.NAME,
        indices = {@Index(value = CardType.Scheme.Properties.NAME, unique = true)})
public class CardType implements CardsFilter.Filterable {

    public interface Scheme {
        String NAME = "types";

        interface Queries {
            String ALL = "select * from " + CardType.Scheme.NAME;
            String ALL_FILER_BY_NAME = "select * from " + CardType.Scheme.NAME + " WHERE " + CardType.Scheme.Properties.NAME + " IN (:names)";
        }

        interface Properties {
            String ID = "id";
            String NAME = "name";
        }
    }

    public CardType(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CardType.Scheme.Properties.ID)
    private long id;
    @ColumnInfo(name = CardType.Scheme.Properties.NAME)
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

    @Override
    public boolean isChecked() {
        return checked;
    }

    @Override
    public void setChecked(boolean checked) {
        this.checked = checked;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardClass)) return false;

        CardType cardType = (CardType) o;

        if (id != cardType.id) return false;
        return name.equals(cardType.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}