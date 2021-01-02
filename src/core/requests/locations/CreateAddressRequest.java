package core.requests.locations;

public class CreateAddressRequest {
	private String street;
	private String houseNumber;
	private String place;
	private String postalCode;
	
	public CreateAddressRequest() {
		super();
	}

	public CreateAddressRequest(String street, String houseNumber, String place, String postalCode) {
		super();
		this.street = street;
		this.houseNumber = houseNumber;
		this.place = place;
		this.postalCode = postalCode;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getHouseNumber() {
		return houseNumber;
	}

	public void setHouseNumber(String houseNumber) {
		this.houseNumber = houseNumber;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}
