package org.arnoid.heartstone.data.relations;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.arnoid.heartstone.data.BaseCard;
import org.arnoid.heartstone.data.CardRarity;

/**
 * Class to map Card-To-Rarity relation.
 */
@Entity(tableName = CardToCardRarity.Scheme.NAME,
        indices = {
                @Index(value = {
                        BaseCard.Scheme.Properties.CARD_ID,
                        CardRarity.Scheme.Properties.ID
                }, unique = true),
        })
public class CardToCardRarity {
    public static final long UNDEFINED = -1;

    public interface Scheme {
        String NAME = "card2rarity";

        interface Properties {
            String ID = "record_id";
            String CARD_ID = BaseCard.Scheme.Properties.CARD_ID;
            String CARD_RARITY_ID = CardRarity.Scheme.Properties.ID;
        }
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Scheme.Properties.ID)
    private Long id;
    @ColumnInfo(name = Scheme.Properties.CARD_ID)
    private String cardId;
    @ColumnInfo(name = Scheme.Properties.CARD_RARITY_ID)
    private long cardRarityId = UNDEFINED;

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

    public long getCardRarityId() {
        return cardRarityId;
    }

    public void setCardRarityId(long cardRarityId) {
        this.cardRarityId = cardRarityId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CardToCardRarity)) return false;

        CardToCardRarity that = (CardToCardRarity) o;

        if (cardRarityId != that.cardRarityId) return false;
        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        return cardId != null ? cardId.equals(that.cardId) : that.cardId == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (cardId != null ? cardId.hashCode() : 0);
        result = 31 * result + (int) (cardRarityId ^ (cardRarityId >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "CardToCardRarity{" +
                "id=" + id +
                ", cardId='" + cardId + '\'' +
                ", cardRarityId=" + cardRarityId +
                '}';
    }
}