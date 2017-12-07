package nl.tom.hstone.model;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonView;

import nl.tom.hstone.rest.View;

/**
 * The main model object.
 * 
 * @author Tom
 *
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card extends AbstractCard {

	@JsonView(View.ListScreen.class)
	String img;// filtering on this field is not allowed

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	String[] classes;
	Mechanics[] mechanics;

	public Mechanics[] getMechanics() {
		return mechanics;
	}

	public void setMechanics(Mechanics[] mechanics) {
		this.mechanics = mechanics;
	}

	public String[] getClasses() {
		return classes;
	}

	public void setClasses(String[] classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Card [cardId=").append(cardId).append(", cardSet=").append(cardSet).append(", name=")
				.append(name).append(", rarity=").append(rarity).append(", type=").append(type).append(", classes=")
				.append(Arrays.toString(classes)).append(", mechanics=").append(Arrays.toString(mechanics)).append("]");
		return builder.toString();
	}

}
