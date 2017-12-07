package nl.tom.hstone.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import nl.tom.hstone.model.AbstractCard;
import nl.tom.hstone.model.Card;
import nl.tom.hstone.model.CardFilter;

/**
 * Card Repository Data Access Object to list / filter / count cards.
 * <p/>
 * Implementation details:
 * <p/>
 * On initialisation the data structure is read from the file, the 2-level tree
 * (cardSet->cards) is flattened to set of cards (cardSet is also a property of each card,
 * so the structure is redundant). No additional index-like data structures are
 * created in this implementation, due to the small size of a dataset. The list
 * is then searched and filtered.
 * <p/>
 * Assuming a very high query:init ratio in long-term, this approach is more
 * cost effective than opening the file on every request and filter during
 * parsing, and the constant memory footprint of the {@link #cardRepository} is
 * negligible.
 * 
 * @author Tom
 *
 */
@Repository
public class CardDaoJacksonImpl implements CardDao {

	private static final String CARDS_JSON = "cards.json";
	private static final int EXPECTED_CARD_AMOUNT = 3116;

	private static final Logger logger = LoggerFactory.getLogger(CardDaoJacksonImpl.class);

	/**
	 * very simple object pool to avoid instantiation-gc of this frequently used
	 * object (over-optimization)
	 */
	private static final Map<SortDirection, CardComparator> comparators;
	static {
		comparators = new HashMap<>();
		comparators.put(SortDirection.ASC, new CardComparator(SortDirection.ASC));
		comparators.put(SortDirection.DESC, new CardComparator(SortDirection.DESC));
	}

	/**
	 * cards are stored here by {@link #init()} and then retrieved by all the other
	 * functions
	 */
	private Set<Card> cardRepository = new HashSet<>(EXPECTED_CARD_AMOUNT);

	/**
	 * Initialisation method to populate {@link #cardRepository} from the json file
	 */
	@PostConstruct
	public void init() {
		logger.info("initialising the repository...");
		try (InputStream fis = new ClassPathResource(CARDS_JSON).getInputStream();) {
			ObjectMapper objectMapper = new ObjectMapper();
			JsonNode rootNode = objectMapper.readTree(fis);
			rootNode.iterator().forEachRemaining(cardSet -> cardSet.iterator().forEachRemaining(cardNode -> {
				try {
					Card card = objectMapper.treeToValue(cardNode, Card.class);
					cardRepository.add(card);
				} catch (JsonProcessingException e) {
					String msg = "Can't process card database file. Repository was not initialised";
					logger.error(msg, e);
					throw new IllegalStateException(msg, e);
				}
			}));
		} catch (IOException e) {
			String msg = "Can't open card database file. Repository was not initialised";
			logger.error(msg, e);
			throw new IllegalStateException(msg, e);
		}
		logger.info("... initialised the repository with {} cards", cardRepository.size());
	}

	public Card getCardById(String cardId) {
		if (cardId == null)
			throw new IllegalArgumentException("cardId must be String");
		CardFilter filter = new CardFilter();
		filter.setCardId(cardId);

		List<Card> filtered = cardRepository.stream()
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getCardId()))).collect(Collectors.toList());

		if (filtered == null)
			return null;
		return filtered.get(0);
	}

	/**
	 * Object oriented filtering approach instead of string / reflection based
	 */
	@Override
	public List<Card> filter(CardFilter filter, SortDirection sortDirection) {
		List<Card> filtered = cardRepository.stream()
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getCardId())))// just to be consistent,
																							// won't be used by the UI
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getType())))
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getRarity())))
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getName())))
				.filter(card -> simpleFilterCondition(card, filter, (c -> c.getCardSet())))
				.filter(card -> executeClassFilter(filter, card))// sepcial filter
				.filter(card -> executeMechanicsFilter(filter, card))// sepcial filter
				.sorted(comparators.get(sortDirection))//
				.collect(Collectors.toList());

		logger.info("returning {} card(s)", filtered.size());
		return filtered;
	}

	/**
	 * Simple {@link java.lang.String#equals(Object)} comparison on the same fields
	 * (inherited from {@link AbstractCard} ) of card & filter.
	 * 
	 * @param card
	 *            card to be checked
	 * @param filter
	 *            filter to be checked
	 * @param func
	 *            method on both the card and filter to be called
	 * @return true if filter is active and card matches filter, false otherwise
	 */
	private boolean simpleFilterCondition(Card card, CardFilter filter, Function<AbstractCard, String> func) {
		// extract filter value
		String filterData = func.apply(filter);

		// if filter is empty, no filtering has to be done
		if (filterData == null || filterData.isEmpty()) {
			return true;
		}
		String cardData = func.apply(card);
		return filterData.equals(cardData);
	}

	/**
	 * Check if any class of the card matches the
	 * {@link CardFilter#getClassFilter()}
	 * 
	 * @param filter
	 * @param card
	 * @return true if filter is active and card matches filter, false otherwise
	 */
	private boolean executeClassFilter(CardFilter filter, Card card) {
		// if filter is empty, no filtering has to be done
		if (filter.getClassFilter() == null || filter.getClassFilter().isEmpty()) {
			return true;
		}

		// if card has no classes it can't match the filter
		if (card.getClasses() == null) {
			return false;
		}

		// check if any class matches the filter
		return Arrays.stream(card.getClasses()).anyMatch(cl -> cl.equals(filter.getClassFilter()));
	}

	/**
	 * Check if any class of the card matches the
	 * {@link CardFilter#getMechanicsFilter()}
	 * 
	 * @param filter
	 * @param card
	 * @return true if filter is active and card matches filter, false otherwise
	 */
	private boolean executeMechanicsFilter(CardFilter filter, Card card) {
		// if filter is empty, no filtering has to be done
		if (filter.getMechanicsFilter() == null || filter.getMechanicsFilter().isEmpty()) {
			return true;
		}

		// if card has no mechanics it can't match the filter
		if (card.getMechanics() == null) {
			return false;
		}

		// check if any mechanic name matches the filter
		return Arrays.stream(card.getMechanics()).anyMatch(mech -> mech.getName().equals(filter.getMechanicsFilter()));
	}

}
