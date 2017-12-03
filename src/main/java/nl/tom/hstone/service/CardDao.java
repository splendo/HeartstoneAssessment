package nl.tom.hstone.service;

import java.util.List;

import nl.tom.hstone.model.Card;
import nl.tom.hstone.model.CardFilter;

/**
 * Card Repository Data Access Object Interface to list / filter / count cards.
 * <p/>
 * Implementations can differ in their nature of repository (eg. in-memory,
 * ElasticSearch, MongoDB), access method, caching, sorting and many more.
 * 
 * @author Tom
 *
 */
public interface CardDao {

	List<Card> filter(CardFilter filter, SortDirection sortDirection);

	Card getCardById(String cardId);

}
