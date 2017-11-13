package org.arnoid.heartstone.data.relations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.CardClass;

/**
 * Class to map Card-To-Class relation.
 */
@Entity(tableName = CardToCardClass.Scheme.NAME,
        indices = {
                @Index(value = {
                        BaseCard.Scheme.Properties.CARD_ID,
                        CardClass.Scheme.Properties.ID
                }, unique = true),
        })
public class CardToCardClass {
    public static final long UNDEFINED = -1;

    public interface Scheme {
        String NAME = "card2class";

        interface Properties {
            String ID = "record_id";
            String CARD_CLASS_ID = CardClass.Scheme.Properties.ID;
            String CARD_ID = BaseCard.Scheme.Properties.CARD_ID;
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    private Long id;
    @ColumnInfo(name = Scheme.Properties.CARD_ID)
    private String cardId;
    @ColumnInfo(name = Scheme.Properties.CARD_CLASS_ID)
    private long cardClassId = UNDEFINED;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public long getCardClassId() {
        return cardClassId;
    }

    public void setCardClassId(long cardClassId) {
        this.cardClassId = cardClassId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardToCardClass)) return false;

        CardToCardClass that = (CardToCardClass) o;

        if (cardClassId != that.cardClassId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return cardId != null ? cardId.equals(that.cardId) : that.cardId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (int) (cardClassId ^ (cardClassId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CardToCardClass{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", cardClassId=" + cardClassId +
                '}';
    }
}
