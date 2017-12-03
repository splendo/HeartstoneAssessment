package nl.tom.hstone.service;

public enum SortDirection {
	ASC(1), DESC(-1);

	private int multiplier;

	SortDirection(int multiplier) {
		this.multiplier = multiplier;
	}

	public int getMultiplier() {
		return multiplier;
	}
}