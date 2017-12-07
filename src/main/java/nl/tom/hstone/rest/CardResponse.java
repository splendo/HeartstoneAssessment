package nl.tom.hstone.rest;

import java.util.List;

import nl.tom.hstone.model.Card;

/**
 * Data transfer object.
 * 
 * @author Tom
 *
 */
public class CardResponse {

	List<Card> cards;

	public CardResponse(List<Card> list) {
		this.cards = list;
	}

	public int getTotalCount() {
		return cards.size();
	}

	public List<Card> getCards() {
		return cards;
	}

	public void setCards(List<Card> cards) {
		this.cards = cards;
	}

	public void setTotalCount(int count) {

	}

}
