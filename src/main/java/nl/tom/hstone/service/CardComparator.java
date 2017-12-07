package nl.tom.hstone.service;

import java.util.Comparator;

import nl.tom.hstone.model.Card;

/**
 * Compares cards by cardId.
 * 
 * @author Tom
 *
 */
public class CardComparator implements Comparator<Card> {

	SortDirection dir;

	public CardComparator(SortDirection dir) {
		this.dir = dir;
	}

	@Override
	public int compare(Card card1, Card card2) {
		// TODO null safe
		return dir.getMultiplier() * card1.getCardId().compareTo(card2.getCardId());
	}
}
