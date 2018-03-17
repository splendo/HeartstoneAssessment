package eu.oloeriu.hearthstone.tools;

import java.util.List;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Card {
    private String cardId;
    private String name;
    private String cardSet;
    private String type;
    private String rarity;
    private List<Mechanic>mechanics;
    private List<String> classes;

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
