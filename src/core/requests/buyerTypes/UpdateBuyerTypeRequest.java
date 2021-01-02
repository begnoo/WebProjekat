package core.requests.buyerTypes;

import java.util.UUID;

public class UpdateBuyerTypeRequest {
	private UUID id;
	private String name;
	private int discount;
	private int minimumPoints;
	
	public UpdateBuyerTypeRequest()
	{
		super();
	}

	public UpdateBuyerTypeRequest(UUID id, String name, int discount, int minimumPoints) {
		super();
		this.id = id;
		this.name = name;
		this.discount = discount;
		this.minimumPoints = minimumPoints;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
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
