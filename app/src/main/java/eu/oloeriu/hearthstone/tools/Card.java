package eu.oloeriu.hearthstone.tools;

import java.util.List;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Card {
    private int cardsCount;
    private int cardsFavorite;
    private String cardId;
    private String name;
    private String cardSet;
    private String type;
    private String rarity;

    private List<Mechanic>mechanics;
    private List<String> classes;
    private String img;
    private String imgGold;
    private String playerClass;


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

    public String getPlayerClass() {
        return playerClass;
    }

    public void setPlayerClass(String playerClass) {
        this.playerClass = playerClass;
    }

    public int getCardsCount() {
        return cardsCount;
    }

    public void setCardsCount(int cardsCount) {
        this.cardsCount = cardsCount;
    }

    public int getCardsFavorite() {
        return cardsFavorite;
    }

    public void setCardsFavorite(int cardsFavorite) {
        this.cardsFavorite = cardsFavorite;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }

    public void setClasses(List<String> classes) {
        this.classes = classes;
    }

    public String getName() {
        return name;
    }

    public String getCardSet() {
        return cardSet;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public List<String> getClasses() {
        return classes;
    }
}
