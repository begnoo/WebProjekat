package core.domain.models;

public class BuyerType extends BaseEntity {
	private String name;
	private int discount;
	private int minimumPoints;
	
	public BuyerType()
	{
		super();
	}

	public BuyerType(String name, int discount, int minimumPoints) {
		super();
		this.name = name;
		this.discount = discount;
		this.minimumPoints = minimumPoints;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getMinimumPoints() {
		return minimumPoints;
	}

	public void setMinimumPoints(int minimumPoints) {
		this.minimumPoints = minimumPoints;
	}
}
