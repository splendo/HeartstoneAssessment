package com.lakhmotkin.heartstonecards.repository.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import java.io.Serializable;
import java.util.ArrayList;

import javax.annotation.Nonnull;

/**
 * Created by Igor Lakhmotkin on 24.02.2018, for HeartstoneAssessment.
 */
@Entity(
        tableName = "cards"
)
public class Card implements Serializable{
    @NonNull
    @PrimaryKey
    private String cardId;
    private String name;
    private String cardSet;
    private String type;
    private String text;
    private String playerClass;
    private String locale;
    private ArrayList<Mechanic> mechanics;
    private String img;
    private String imgGold;
    private Boolean collectible;
    private Integer health;
    private String faction;
    private String rarity;
    private boolean favorite = false;

    public Card(@Nonnull String cardId, String name, String cardSet, String type, String text, String playerClass, String locale, ArrayList<Mechanic> mechanics, String img, String imgGold, Boolean collectible, Integer health, String faction, String rarity, boolean favorite) {
        this.cardId = cardId;
        this.name = name;
        this.cardSet = cardSet;
        this.type = type;
        this.text = text;
        this.playerClass = playerClass;
        this.locale = locale;
        this.mechanics = mechanics;
        this.img = img;
        this.imgGold = imgGold;
        this.collectible = collectible;
        this.health = health;
        this.faction = faction;
        this.rarity = rarity;
        this.favorite = favorite;
    }

    public Card(String cardId, String name, String cardSet, String img, String imgGold) {
        this.cardId = cardId;
        this.name = name;
        this.cardSet = cardSet;
        this.img = img;
        this.imgGold = imgGold;
    }

    public Card(@NonNull String cardId) {
        this.cardId = cardId;
    }

    public Card() {
    }

    public String getMechanicsString() {
        if (this.mechanics == null) {
            return "";
        }
        StringBuilder mechanicsString = new StringBuilder();
        for (Mechanic mechanic: this.mechanics) {
            mechanicsString.append(mechanic.getName()).append("\n");
        }
        return mechanicsString.toString();
    }

    @NonNull
    public String getCardId() {
        return cardId;
    }

    public void setCardId(@NonNull String cardId) {
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public String getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = locale;
    }

    public ArrayList<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(ArrayList<Mechanic> mechanics) {
        this.mechanics = mechanics;
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

    public Boolean getCollectible() {
        return collectible;
    }

    public void setCollectible(Boolean collectible) {
        this.collectible = collectible;
    }

    public Integer getHealth() {
        return health;
    }

    public void setHealth(Integer health) {
        this.health = health;
    }

    public String getFaction() {
        return faction;
    }

    public void setFaction(String faction) {
        this.faction = faction;
    }

    public boolean getFavorite() {
        return favorite;
    }

    public void setFavorite(boolean favorite) {
        this.favorite = favorite;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

}
