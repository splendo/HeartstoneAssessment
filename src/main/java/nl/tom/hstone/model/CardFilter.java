package nl.tom.hstone.model;

/**
 * Represents all filter conditions.
 * 
 * @author Tom
 *
 */
public class CardFilter extends AbstractCard {

	String classFilter;

	String mechanicsFilter;

	public String getMechanicsFilter() {
		return mechanicsFilter;
	}

	public void setMechanicsFilter(String mechanicsFilter) {
		this.mechanicsFilter = mechanicsFilter;
	}

	public String getClassFilter() {
		return classFilter;
	}

	public void setClassFilter(String classFilter) {
		this.classFilter = classFilter;
	}

}
