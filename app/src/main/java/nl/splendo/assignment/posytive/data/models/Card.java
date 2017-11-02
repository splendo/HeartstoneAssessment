package nl.splendo.assignment.posytive.data.models;


import android.util.Log;

import com.google.gson.annotations.SerializedName;
import nl.splendo.assignment.posytive.data.Types;
import nl.splendo.assignment.posytive.data.models.attributes.CardClass;
import nl.splendo.assignment.posytive.data.models.attributes.CardClass_Table;
import nl.splendo.assignment.posytive.data.models.attributes.Mechanic;
import nl.splendo.assignment.posytive.data.models.attributes.Mechanic_Table;
import nl.splendo.assignment.posytive.helpers.db.LocalDatabase;
import nl.splendo.assignment.posytive.mvp.bindings.GenericMVPBinding;
import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.config.DatabaseDefinition;
import com.raizlabs.android.dbflow.sql.language.SQLite;
import com.raizlabs.android.dbflow.sql.language.property.Property;
import com.raizlabs.android.dbflow.structure.BaseModel;
import com.raizlabs.android.dbflow.structure.database.transaction.Transaction;

import org.parceler.Parcel;

import java.util.ArrayList;
import java.util.List;

/**
 * The model representing a Card item
 * It contains information about the name, rarity and class of the Card
 * This model should be able to be fetched from:
 * - network (with retrofit)
 * - local embedded file (for testing purposes, not planning to embed because is "too big")
 * - Database
 */
@Table(database = LocalDatabase.class)
@Parcel(value = Parcel.Serialization.BEAN, analyze = {Card.class})
public class Card extends BaseModel implements GenericMVPBinding.ListableModel, GenericMVPBinding.FavoritableModel {

    public static String MODEL_TYPE = Types.Item.CARD;

    /** Each Card has a unique text to identify it */
    @SerializedName("cardId")
    @PrimaryKey
    private String id;

    @Column
    @SerializedName("type")
    private String cardType;

    /** Name/Title of the Card */
    @SerializedName("name")
    @Column
    private String name;

    @SerializedName("text")
    @Column
    private String text;

    @SerializedName("cardSet")
    @Column
    private String cardSet;

    @SerializedName("faction")
    @Column
    private String faction;

    @SerializedName("rarity")
    @Column
    private String rarity;

    @SerializedName("cost")
    @Column
    private int cost = -1;

    @SerializedName("attack")
    @Column
    private int attack;

    @SerializedName("health")
    @Column
    private int health;

    @SerializedName("flavor")
    @Column
    private String flavor;

    @SerializedName("artist")
    @Column
    private String artist;

    @SerializedName("collectible")
    @Column
    private boolean collectible;

    @SerializedName("elite")
    @Column
    private boolean elite;

    @SerializedName("playerClass")
    @Column
    private String playerClass;

    @SerializedName("multiClassGroup")
    @Column
    private String multiClassGroup;

    @SerializedName("img")
    @Column
    private String img;

    @SerializedName("imgGold")
    @Column
    private String imgGold;

    @SerializedName("locale")
    @Column
    private String locale;

    @SerializedName("race")
    @Column
    private String race;

    @SerializedName("howToGet")
    @Column
    private String howToGet;

    @SerializedName("howToGetGold")
    @Column
    private String howToGetGold;

    @SerializedName("classes")
    private List<String> classes;

    @SerializedName("mechanics")
    private List<Mechanic>  mechanics;

    private List<CardClass> classesDB;

    @Column
    @SerializedName("noRemoteFavorite")
    private Boolean isFavorite;

    public Card() { super(); }

    public Card(Card anotherToCopy) {
        this.id = anotherToCopy.getId();
        this.name = anotherToCopy.getName();
        this.isFavorite = anotherToCopy.isFavorite;
        this.text = anotherToCopy.getText();
        this.cardSet = anotherToCopy.getCardSet();
        this.faction = anotherToCopy.getFaction();
        this.rarity = anotherToCopy.getRarity();
        this.cost = anotherToCopy.getCost();
        this.attack = anotherToCopy.getAttack();
        this.health = anotherToCopy.getHealth();
        this.flavor = anotherToCopy.getFlavor();
        this.artist = anotherToCopy.getArtist();
        this.collectible = anotherToCopy.isCollectible();
        this.elite = anotherToCopy.isElite();
        this.playerClass = anotherToCopy.getPlayerClass();
        this.multiClassGroup = anotherToCopy.getMultiClassGroup();
        this.img = anotherToCopy.getImg();
        this.imgGold = anotherToCopy.getImgGold();
        this.locale = anotherToCopy.getLocale();
        this.race = anotherToCopy.getRace();
        this.howToGet = anotherToCopy.getHowToGet();
        this.howToGetGold = anotherToCopy.getHowToGetGold();
        this.classes = anotherToCopy.classes;
        this.classesDB = anotherToCopy.classesDB;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Card && id.equals(((Card) obj).getId());
    }

    public List<CardClass> getCardClassesFromDb() {
        Log.d(MODEL_TYPE, "getCardClassesFromDb()");
        if (classesDB == null || classesDB.isEmpty()) {
            classesDB = SQLite.select()
                    .from(CardClass.class)
                    .where(CardClass_Table.cardId.eq(id))
                    .queryList();
        }
        if (classesDB != null && (classes == null || classes.isEmpty())) {
            classes = new ArrayList<>();
            for (CardClass cardClass : classesDB) classes.add(cardClass.getCardClassName());
        }
        return classesDB;
    }

    public void saveCardClassesInDb(DatabaseDefinition dbConfig, List<CardClass> cardClasses) {
        Log.w(MODEL_TYPE, "saveCardClassesInDb()");
        Transaction transaction = dbConfig.beginTransactionAsync(
                databaseWrapper -> {
                    for (CardClass cardClass : cardClasses) {
                        cardClass.save();
                    }
                }).build();
        transaction.execute();
    }

    private void syncCardClasses(DatabaseDefinition dbConfig) {
        if (classesDB == null && classes != null) {
            List<CardClass> newCardClasses = new ArrayList<>();
            for (String className : classes) {
                CardClass created = new CardClass();
                created.setCardId(id);
                created.setCardClassName(className);
                newCardClasses.add(created);
            }
            classesDB = newCardClasses;
        }
        if (classes != null && !classes.isEmpty()) saveCardClassesInDb(dbConfig, classesDB);
    }

    public List<Mechanic> getMechanicsFromDb() {
        if (mechanics == null || mechanics.isEmpty()) {
            mechanics = SQLite.select()
                    .from(Mechanic.class)
                    .where(Mechanic_Table.cardId.eq(id))
                    .queryList();
        }
        return mechanics;
    }

    public void saveMechanicsInDb(DatabaseDefinition dbConfig, List<Mechanic> mechanicList) {
        Log.w(MODEL_TYPE, "saveMechanicsInDb()");
        Transaction transaction = dbConfig.beginTransactionAsync(
                databaseWrapper -> {
                    for (Mechanic mechanic : mechanicList) {
                        mechanic.setCardId(id);
                        mechanic.save();
                    }
                }).build();
        transaction.execute();
    }

    @Override
    public void storeAttributes(DatabaseDefinition dbConfig) {
        if (mechanics != null) saveMechanicsInDb(dbConfig, mechanics);
        syncCardClasses(dbConfig);
    }

    @Override
    public void loadAttributes(DatabaseDefinition dbConfig) {
        getMechanicsFromDb();
        getCardClassesFromDb();
    }

    @Override
    public String getTitle() {
        return getName();
    }

    public String getCardType() {
        return cardType;
    }

    @Override
    public String getModelType() {
        return MODEL_TYPE;
    }

    public void setType(String type) { /* do nothing, only to please DbFlow lib */ }

    @Override
    @SuppressWarnings("unchecked")
    public Property getPrimaryColumn() {
        return Card_Table.id;
    }

    @Override
    public String toString() {
        return String.format(getModelType() + ": %s - %s @ %s", getId(), getTitle(), getRarity());
    }

    /* Generated getters and setters */

    @Override
    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }

    public int getAttack() {
        return attack;
    }

    public int getHealth() {
        return health;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public void setHealth(int health) {
        this.health = health;
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

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getHowToGet() {
        return howToGet;
    }

    public void setHowToGet(String howToGet) {
        this.howToGet = howToGet;
    }

    public String getHowToGetGold() {
        return howToGetGold;
    }

    public void setHowToGetGold(String howToGetGold) {
        this.howToGetGold = howToGetGold;
    }

    public void setCardType(String cardType) {
        this.cardType = cardType;
    }

    @Override
    public Boolean isFavorite() {
        if (isFavorite != null) return isFavorite;
        return false;
    }

    @Override
    public void setFavorite(Boolean favorite) {
        if (favorite != null) isFavorite = favorite;
    }

    @Override
    public GenericMVPBinding.Model makeCopy() {
        return new Card(this);
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public List<String> getClasses() {
        return classes;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public List<CardClass> getClassesDB() {
        return classesDB;
    }

    public void setClassesDB(List<CardClass> classesDB) {
        this.classesDB = classesDB;
    }
}
