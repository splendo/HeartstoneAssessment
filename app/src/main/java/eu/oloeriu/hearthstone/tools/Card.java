package eu.oloeriu.hearthstone.tools;

import java.util.List;

/**
 * Created by Bogdan Oloeriu on 15/03/2018.
 */

public class Card {
    private String cardId;
    private String name;
    private String cardSet;
    private List<Mechanic>mechanics;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCardId() {
        return cardId;
    }

    public void setCardId(String cardId) {
        this.cardId = cardId;
    }

    public String getCardSet() {
        return cardSet;
    }

    public void setCardSet(String cardSet) {
        this.cardSet = cardSet;
    }

    public List<Mechanic> getMechanics() {
        return mechanics;
    }

    public void setMechanics(List<Mechanic> mechanics) {
        this.mechanics = mechanics;
    }
}
