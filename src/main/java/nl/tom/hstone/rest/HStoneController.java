package nl.tom.hstone.rest;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import nl.tom.hstone.model.Card;
import nl.tom.hstone.model.CardFilter;
import nl.tom.hstone.service.CardDao;
import nl.tom.hstone.service.SortDirection;

@RestController
public class HStoneController {

	private static final Logger logger = LoggerFactory.getLogger(HStoneController.class);

	/**
	 * If the maximum is not specified, this is the default
	 */
	private static final Integer DEFAULT_MAX = 5000;

	@Autowired
	CardDao cardDao;

	// TODO: improve error handling

	@JsonView(View.ListScreen.class)
	@RequestMapping("/rest/cards/{dir}/")
	public Map<String, Object> filter(@PathVariable String dir, @ModelAttribute CardFilter filter,
			@RequestParam(defaultValue = "0") int from, Integer max) throws IOException {
		List<Card> list = cardDao.filter(filter, SortDirection.valueOf(dir));
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("total", list.size());
		//FIXME: expand this and move it to the Dao, the pagination should be there
		int toIndex = Math.min(list.size(), from + ((max == null) ? DEFAULT_MAX : max));
		List<Card> resultList = null;
		if(from > toIndex) {
			resultList = Collections.emptyList();
		} else {
			resultList = list.subList(from, toIndex);
		}
		map.put("list", resultList);
		return map;
	}

	@RequestMapping("/rest/card/{cardId}")
	public Card getCardById(@PathVariable String cardId) throws IOException {
		return cardDao.getCardById(cardId);
	}

	@RequestMapping("/hello")
	public String hello() {
		String msg = "Hello Splendo!";
		logger.info(msg);
		return msg;
	}

}
