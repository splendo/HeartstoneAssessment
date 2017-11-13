package org.arnoid.heartstone.data;

import android.arch.persistence.room.Relation;

import java.util.Set;

public class CardComplete extends Card {
    @Relation(parentColumn = Card.Scheme.Properties.NAME,
            entityColumn = CardClass.Scheme.Properties.NAME)
    private Set<CardClass> classes;
    @Relation(parentColumn = Card.Scheme.Properties.NAME,
            entityColumn = Mechanic.Scheme.Properties.NAME)
    private Set<Mechanic> mechanics;
    @Relation(parentColumn = Card.Scheme.Properties.NAME,
            entityColumn = CardType.Scheme.Properties.NAME)
    private Set<CardType> type;
    @Relation(parentColumn = Card.Scheme.Properties.NAME,
            entityColumn = CardRarity.Scheme.Properties.NAME)
    private Set<CardRarity> rarity;

    public Set<CardClass> getClasses() {
        return classes;
    }

    public void setClasses(Set<CardClass> classes) {
        this.classes = classes;
    }

    public Set<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(Set<Mechanic> mechanics) {
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
        if (!(o instanceof CardComplete)) return false;
        if (!super.equals(o)) return false;

        CardComplete that = (CardComplete) o;

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
        return "CardComplete{" +
                "classes=" + classes +
                ", mechanics=" + mechanics +
                ", type=" + type +
                ", rarity=" + rarity +
                "} " + super.toString();
    }
}
