package com.lakhmotkin.heartstonecards.repository.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Igor Lakhmotkin on 24.02.2018, for HeartstoneAssessment.
 */

public class Card implements Serializable{
    private String cardId;
    private String name;
    private String cardSet;
    private String type;
    private String text;
    private String playerClass;
    private String locale;
    private List<Mechanic> mechanics;
    private String img;
    private String imgGold;
    private Boolean collectible;
    private Integer health;
    private String faction;

    public Card(String cardId, String name, String cardSet, String img, String imgGold) {
        this.cardId = cardId;
        this.name = name;
        this.cardSet = cardSet;
        this.img = img;
        this.imgGold = imgGold;
    }

    public Card(String cardId) {
        this.cardId = cardId;
    }

    public Card() {
    }

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

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
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
}
