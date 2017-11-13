package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = CardType.Scheme.NAME)
public class CardType {

    public interface Scheme {
        String NAME = "types";

        interface Properties {
            String NAME = "name";
        }
    }

    public CardType(String name) {
        this.name = name;
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Properties.NAME)
    @NonNull
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardType)) return false;

        CardType otherType = (CardType) o;

        return name != null ? name.equals(otherType.name) : otherType.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "CardType{" +
                "name='" + name + '\'' +
                '}';
    }

}