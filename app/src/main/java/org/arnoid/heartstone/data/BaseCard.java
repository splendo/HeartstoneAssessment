package org.arnoid.heartstone.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity(tableName = Card.Scheme.NAME)
public class Card {

    public enum Rarity {
        UNKNOWN,
        BASIC,
        COMMON,
        RARE,
        EPIC,
        LEGENDARY;

        public static Rarity parse(String strRarity) {
            for (Rarity rarity : Rarity.values()) {
                if (rarity.name().equalsIgnoreCase(strRarity)) {
                    return rarity;
                }
            }

            return UNKNOWN;
        }
    }

    public interface Scheme {
        String NAME = "cards";

        interface Properties {
            String CARD_ID = "cardId";
            String NAME = "name";
            String IMAGE = "img";
        }

        interface Queries {
            String ALL = "SELECT * FROM " + Scheme.NAME + " ORDER BY " + Properties.CARD_ID;
            String ALL_CARD_ID = "SELECT " + Properties.CARD_ID + " FROM " + Scheme.NAME + " ORDER BY " + Properties.CARD_ID;
            String CARD_BY_ID = "SELECT * FROM " + Scheme.NAME + " WHERE " + Properties.CARD_ID + " = :cardId LIMIT 1";
        }
    }

    @PrimaryKey
    @ColumnInfo(name = Scheme.Properties.CARD_ID)
    @NonNull
    private String cardId;
    @ColumnInfo(name = Scheme.Properties.NAME)
    private String name;
    private String cardSet;
    private int cost;
    private int attack;
    private int health;
    private String text;
    private String flavor;
    private String artist;
    private boolean collectible;
    private boolean elite;
    private String playerClass;
    private String multiClassGroup;
    @ColumnInfo(name = Scheme.Properties.IMAGE)
    private String img;
    private String imgGold;
    private String locale;
    private boolean favourite;

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public boolean isCollectible() {
        return collectible;
    }

    public void setCollectible(boolean collectible) {
        this.collectible = collectible;
    }

    public boolean isElite() {
        return elite;
    }

    public void setElite(boolean elite) {
        this.elite = elite;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getMultiClassGroup() {
        return multiClassGroup;
    }

    public void setMultiClassGroup(String multiClassGroup) {
        this.multiClassGroup = multiClassGroup;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getImgGold() {
        return imgGold;
    }

    public void setImgGold(String imgGold) {
        this.imgGold = imgGold;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public boolean isFavourite() {
        return favourite;
    }

    public void setFavourite(boolean favourite) {
        this.favourite = favourite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Card)) return false;

        Card card = (Card) o;

        if (cost != card.cost) return false;
        if (attack != card.attack) return false;
        if (health != card.health) return false;
        if (collectible != card.collectible) return false;
        if (elite != card.elite) return false;
        if (favourite != card.favourite) return false;
        if (!cardId.equals(card.cardId)) return false;
        if (name != null ? !name.equals(card.name) : card.name != null) return false;
        if (cardSet != null ? !cardSet.equals(card.cardSet) : card.cardSet != null) return false;
        if (text != null ? !text.equals(card.text) : card.text != null) return false;
        if (flavor != null ? !flavor.equals(card.flavor) : card.flavor != null) return false;
        if (artist != null ? !artist.equals(card.artist) : card.artist != null) return false;
        if (playerClass != null ? !playerClass.equals(card.playerClass) : card.playerClass != null)
            return false;
        if (multiClassGroup != null ? !multiClassGroup.equals(card.multiClassGroup) : card.multiClassGroup != null)
            return false;
        if (img != null ? !img.equals(card.img) : card.img != null) return false;
        if (imgGold != null ? !imgGold.equals(card.imgGold) : card.imgGold != null) return false;
        return locale != null ? locale.equals(card.locale) : card.locale == null;
    }

    @Override
    public int hashCode() {
        int result = cardId.hashCode();
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (cardSet != null ? cardSet.hashCode() : 0);
        result = 31 * result + cost;
        result = 31 * result + attack;
        result = 31 * result + health;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (flavor != null ? flavor.hashCode() : 0);
        result = 31 * result + (artist != null ? artist.hashCode() : 0);
        result = 31 * result + (collectible ? 1 : 0);
        result = 31 * result + (elite ? 1 : 0);
        result = 31 * result + (playerClass != null ? playerClass.hashCode() : 0);
        result = 31 * result + (multiClassGroup != null ? multiClassGroup.hashCode() : 0);
        result = 31 * result + (img != null ? img.hashCode() : 0);
        result = 31 * result + (imgGold != null ? imgGold.hashCode() : 0);
        result = 31 * result + (locale != null ? locale.hashCode() : 0);
        result = 31 * result + (favourite ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Card{" +
                "cardId='" + cardId + '\'' +
                ", name='" + name + '\'' +
                ", cardSet='" + cardSet + '\'' +
                ", cost=" + cost +
                ", attack=" + attack +
                ", health=" + health +
                ", text='" + text + '\'' +
                ", flavor='" + flavor + '\'' +
                ", artist='" + artist + '\'' +
                ", collectible=" + collectible +
                ", elite=" + elite +
                ", playerClass='" + playerClass + '\'' +
                ", multiClassGroup='" + multiClassGroup + '\'' +
                ", img='" + img + '\'' +
                ", imgGold='" + imgGold + '\'' +
                ", locale='" + locale + '\'' +
                ", favourite=" + favourite +
                '}';
    }
}
