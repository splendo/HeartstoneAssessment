package eu.oloeriu.hearthstone.data;

import java.util.List;

import ckm.simple.sql_provider.annotation.SimpleSQLColumn;
import ckm.simple.sql_provider.annotation.SimpleSQLTable;
import eu.oloeriu.hearthstone.tools.Card;
import eu.oloeriu.hearthstone.tools.Mechanic;

/**
 * Created by Bogdan Oloeriu on 17/03/2018.
 */

@SimpleSQLTable(table = "card", provider = "HearthstoneProvider")

public class CardSql {

    @SimpleSQLColumn(value = "_id", primary = true)
    private String cardId;

    @SimpleSQLColumn("name")
    private String name;

    @SimpleSQLColumn("cardsCount")
    private int cardsCount;

    @SimpleSQLColumn("cardsFavorite")
    private int cardsFavorite;

    @SimpleSQLColumn("cardSet")
    private String cardSet;

    @SimpleSQLColumn("type")
    private String type;

    @SimpleSQLColumn("rarity")
    private String rarity;

    @SimpleSQLColumn("mechanics")
    private String mechanics;

    @SimpleSQLColumn("classes")
    private String classes;

    @SimpleSQLColumn("img")
    private String img;

    @SimpleSQLColumn("imgGold")
    private String imgGold;

    @SimpleSQLColumn("playerClass")
    private String playerClass;


    public CardSql() {

    }

    public static CardSql buildFromCard(Card card) {
        CardSql sqlCard = new CardSql();
        sqlCard.setCardsCount(card.getCardsCount());
        sqlCard.setCardsFavorite(card.getCardsFavorite());
        sqlCard.setCardId(card.getCardId());
        sqlCard.setName(card.getName());
        sqlCard.setCardSet(card.getCardSet());
        sqlCard.setType(card.getType());
        sqlCard.setRarity(card.getRarity());

        StringBuilder mechanics = new StringBuilder();
        mechanics.append("");
        if (null != card.getMechanics()) {
            for (Mechanic mechanic : card.getMechanics()) {
                mechanics.append(mechanic.getName()+" ");
            }
        }
        sqlCard.setMechanics(mechanics.toString());

        StringBuilder classes = new StringBuilder();
        classes.append("");
        if (null != card.getClasses()) {
            for (String itemClass : card.getClasses()) {
                classes.append(itemClass +" ");
            }
        }
        sqlCard.setClasses(classes.toString());

        sqlCard.setImg(card.getImg());
        sqlCard.setImgGold(card.getImgGold());
        sqlCard.setPlayerClass(card.getPlayerClass());
        return sqlCard;
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

    public String getRarity() {
        return rarity;
    }

    public void setRarity(String rarity) {
        this.rarity = rarity;
    }

    public String getMechanics() {
        return mechanics;
    }

    public void setMechanics(String mechanics) {
        this.mechanics = mechanics;
    }

    public String getClasses() {
        return classes;
    }

    public void setClasses(String classes) {
        this.classes = classes;
    }
}
