package org.arnoid.heartstone.data.relations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.CardMechanic;

/**
 * Class to map Card-To-Mechanic relation.
 */
@Entity(tableName = CardToCardMechanic.Scheme.NAME,
        indices = {
                @Index(value = {
                        BaseCard.Scheme.Properties.CARD_ID,
                        CardMechanic.Scheme.Properties.ID
                }, unique = true),
        })
public class CardToCardMechanic {
    public static long UNDEFINED = -1;

    public interface Scheme {
        String NAME = "card2mechanic";

        interface Properties {
            String ID = "record_id";
            String CARD_ID = BaseCard.Scheme.Properties.CARD_ID;
            String CARD_MECHANIC_ID = CardMechanic.Scheme.Properties.ID;
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    private Long id;
    @ColumnInfo(name = Scheme.Properties.CARD_ID)
    private String cardId;
    @ColumnInfo(name = Scheme.Properties.CARD_MECHANIC_ID)
    private long cardMechanicId = UNDEFINED;

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

    public long getCardMechanicId() {
        return cardMechanicId;
    }

    public void setCardMechanicId(long cardMechanicId) {
        this.cardMechanicId = cardMechanicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardToCardMechanic)) return false;

        CardToCardMechanic that = (CardToCardMechanic) o;

        if (cardMechanicId != that.cardMechanicId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return cardId != null ? cardId.equals(that.cardId) : that.cardId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (int) (cardMechanicId ^ (cardMechanicId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CardToCardMechanic{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", cardMechanicId=" + cardMechanicId +
                '}';
    }
}
