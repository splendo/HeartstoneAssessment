package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import org.arnoid.heartstone.data.util.CardsFilter;

/**
 * Entity to represent card rarity.
 */
@Entity(tableName = CardRarity.Scheme.NAME,
        indices = {@Index(value = CardRarity.Scheme.Properties.NAME, unique = true)})
public class CardRarity implements CardsFilter.Filterable {

    public interface Scheme {
        String NAME = "rarity";

        interface Queries {
            String ALL = "select * from " + CardRarity.Scheme.NAME;
            String ALL_FILER_BY_NAME = "select * from " + CardRarity.Scheme.NAME + " WHERE " + CardRarity.Scheme.Properties.NAME + " IN (:names)";
        }

        interface Properties {
            String ID = "id";
            String NAME = "name";
        }
    }

    public CardRarity(String name) {
        this.name = name;
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = CardRarity.Scheme.Properties.ID)
    private long id;
    @ColumnInfo(name = CardRarity.Scheme.Properties.NAME)
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

        CardRarity cardRarity = (CardRarity) o;

        if (id != cardRarity.id) return false;
        return name.equals(cardRarity.name);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "CardRarity{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

}