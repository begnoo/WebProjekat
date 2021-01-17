package core.repository;

public enum SortingOrder {
	Ascending(1), Descending(-1);
	
	private int modifier;

	SortingOrder(int modifier) {
		this.modifier = modifier;
	}

	public int getModifier() {
		return this.modifier;
	}

}
