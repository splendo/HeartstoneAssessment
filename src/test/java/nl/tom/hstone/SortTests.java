package nl.tom.hstone;

import static nl.tom.hstone.service.SortDirection.ASC;
import static nl.tom.hstone.service.SortDirection.DESC;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Collections;
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
public class SortTests {
	
	private static final Logger logger = LoggerFactory.getLogger(SortTests.class);

	@Autowired
	CardDao cardDao;
	
	@Test
	public void filterMechanicsSorted() {
		CardFilter filter = new CardFilter();
		filter.setMechanicsFilter("Deathrattle");
		List<Card> resultAsc = cardDao.filter(filter,  ASC);
		List<Card> resultDesc = cardDao.filter(filter,  DESC);
		assertEquals(resultDesc.size(), resultAsc.size());
		
		reverseCheck(resultAsc, resultDesc);
	}

	@Test
	public void noFilterSorted() {
		CardFilter filter = new CardFilter();
		List<Card> resultAsc = cardDao.filter(filter,  ASC);
		assertEquals(3116, resultAsc.size());
		List<Card> resultDesc = cardDao.filter(filter,  DESC);
		assertEquals(3116, resultDesc.size());
		
		reverseCheck(resultAsc, resultDesc);
	}

	private void reverseCheck(List<Card> resultAsc, List<Card> resultDesc) {
		// manual
		for (int i = 0; i < resultDesc.size()/2; i++) {
			assertEquals(resultAsc.get(i), resultDesc.get(resultDesc.size()-1-i));
		}
		
		//using reverse
		List<Card> reversed = new ArrayList<>(resultAsc);
		Collections.reverse(reversed);
		
		assertArrayEquals(
				resultDesc.stream().toArray(Card[]::new), 
				reversed.stream().toArray(Card[]::new));
		
	}

}
