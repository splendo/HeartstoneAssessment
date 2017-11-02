package nl.splendo.assignment.posytive.data.models.responses;

import com.google.gson.annotations.SerializedName;
import nl.splendo.assignment.posytive.data.models.Card;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Response that maps different list of Cards into a single one
 */
public class CardsListResponse implements ListResponse<Card> {

    /** List of cards as returned by API (grouped under key) */
    @SerializedName("cards")
    Map<String, List<Card>> cards;

    public CardsListResponse(Map<String, List<Card>> cardsResponseBody) {
        this.cards = cardsResponseBody;
    }

    public List<Card> getCards() {
        return getDataAsList();
    }

    @Override
    public List<Card> getDataAsList() {
        List allCards = new LinkedList();
        if (cards != null) {
            for (Map.Entry<String, List<Card>> entry : cards.entrySet()) {
                allCards.addAll(entry.getValue());
            }
        }
        return allCards;
    }
}
