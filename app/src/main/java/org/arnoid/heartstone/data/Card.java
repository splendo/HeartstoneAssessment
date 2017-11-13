package org.arnoid.heartstone.data;

import android.arch.persistence.room.Embedded;
import android.arch.persistence.room.Relation;

import com.google.gson.annotations.SerializedName;

import java.util.Set;

/**
 * Composite class to represent complete card data.
 *
 * Filtering options like Rarity, Classes, Types, Mechanics are Sets to comply the @Relation
 * requirements.
 */
public class Card {

    public interface Scheme {
        interface Properties {
            String CLASSES = "classes";
            String MECHANICS = "mechanics";
            String TYPE = "type";
            String RARITY = "rarity";
        }
    }

    @Embedded
    private BaseCard baseCard;

    @Relation(parentColumn = BaseCard.Scheme.Properties.CARD_ID,
            entityColumn = CardClass.Scheme.Properties.ID,
            entity = CardClass.class)
    @SerializedName(Scheme.Properties.CLASSES)
    private Set<CardClass> classes;
    @Relation(parentColumn = BaseCard.Scheme.Properties.CARD_ID,
            entityColumn = CardMechanic.Scheme.Properties.ID,
            entity = CardMechanic.class)
    @SerializedName(Scheme.Properties.MECHANICS)
    private Set<CardMechanic> mechanics;
    @Relation(parentColumn = BaseCard.Scheme.Properties.CARD_ID,
            entityColumn = CardType.Scheme.Properties.ID,
            entity = CardType.class)
    @SerializedName(Scheme.Properties.TYPE)
    private Set<CardType> type;
    @Relation(parentColumn = BaseCard.Scheme.Properties.CARD_ID,
            entityColumn = CardRarity.Scheme.Properties.ID,
            entity = CardRarity.class)
    @SerializedName(Scheme.Properties.RARITY)
    private Set<CardRarity> rarity;

    public BaseCard getBaseCard() {
        return baseCard;
    }

    public void setBaseCard(BaseCard baseCard) {
        this.baseCard = baseCard;
    }

    public Set<CardClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<CardClass> classes) {
        this.classes = classes;
    }

    public Set<CardMechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(Set<CardMechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public Set<CardType> getType() {
        return type;
    }

    public void setType(Set<CardType> type) {
        this.type = type;
    }

    public Set<CardRarity> getRarity() {
        return rarity;
    }

    public void setRarity(Set<CardRarity> rarity) {
        this.rarity = rarity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;
        if (!super.equals(o)) return false;

        Card that = (Card) o;

        if (classes != null ? !classes.equals(that.classes) : that.classes != null) return false;
        if (mechanics != null ? !mechanics.equals(that.mechanics) : that.mechanics != null)
            return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        return rarity != null ? rarity.equals(that.rarity) : that.rarity == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (classes != null ? classes.hashCode() : 0);
        result = 31 * result + (mechanics != null ? mechanics.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (rarity != null ? rarity.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "baseCard=" + baseCard +
                ", classes=" + classes +
                ", mechanics=" + mechanics +
                ", type=" + type +
                ", rarity=" + rarity +
                '}';
    }
}
