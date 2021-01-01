package core.domain.enums;

public enum TicketType {
	Vip(2), Regular(1), FanPit(4);

	private int modifier;

	TicketType(int modifier) {
		this.modifier = modifier;
	}

	public int getModifier() {
		return this.modifier;
	}
}
