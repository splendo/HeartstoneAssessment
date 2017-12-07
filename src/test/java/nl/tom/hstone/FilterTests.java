package nl.tom.hstone;

import static nl.tom.hstone.service.SortDirection.ASC;
import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import nl.tom.hstone.model.Card;
import nl.tom.hstone.model.CardFilter;
import nl.tom.hstone.service.CardDao;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FilterTests {
	
	private static final Logger logger = LoggerFactory.getLogger(FilterTests.class);

	@Autowired
	CardDao cardDao;
	
	@Test
	public void filterOneCard() {
		CardFilter filter = new CardFilter();
		filter.setCardId("CFM_902");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(1, result.size());
		assertEquals("Legendary", result.get(0).getRarity());
		assertEquals("Minion", result.get(0).getType());
		assertEquals("Aya Blackpaw", result.get(0).getName());
		assertEquals("Mean Streets of Gadgetzan", result.get(0).getCardSet());
	}
	
	@Test
	public void getOneCard() {
		Card result = cardDao.getCardById("CFM_902");
		
		assertEquals("Legendary", result.getRarity());
		assertEquals("Minion", result.getType());
		assertEquals("Aya Blackpaw", result.getName());
		assertEquals("Mean Streets of Gadgetzan", result.getCardSet());
	}
	
	@Test
	public void filterByNameAndCardSet() {
		CardFilter filter = new CardFilter();
		filter.setName("Stalagg");
		filter.setCardSet("Naxxramas");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(2, result.size());
	}
	
	@Test
	public void filterClass() {
		CardFilter filter = new CardFilter();
		filter.setClassFilter("Shaman");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(3, result.size());
	}
	
	@Test
	public void filterMechanics() {
		CardFilter filter = new CardFilter();
		filter.setMechanicsFilter("Deathrattle");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(155, result.size());
	}
	
	@Test
	public void filterClassAndMechanics() {
		CardFilter filter = new CardFilter();
		filter.setClassFilter("Shaman");
		filter.setMechanicsFilter("Deathrattle");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(1, result.size());
	}
	
	@Test
	public void filterHsiaoFavourites() {
		CardFilter filter = new CardFilter();
		filter.setRarity("Legendary");
		filter.setMechanicsFilter("Deathrattle");
		
		List<Card> result = cardDao.filter(filter,  ASC);
		
		assertEquals(31, result.size());
	}

}
