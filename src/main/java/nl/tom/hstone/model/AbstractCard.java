package nl.tom.hstone.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import nl.tom.hstone.rest.View;

/**
 * Simple non-structured fields of a card that can also be filtered on.
 * 
 * @author Tom
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public abstract class AbstractCard {

	@JsonView(View.ListScreen.class)
	String cardId;
	String name;
	String cardSet;

	String type;
	String rarity;

	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCardSet() {
		return cardSet;
	}

	public void setCardSet(String cardSet) {
		this.cardSet = cardSet;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getRarity() {
		return rarity;
	}

	public void setRarity(String rarity) {
		this.rarity = rarity;
	}

}
