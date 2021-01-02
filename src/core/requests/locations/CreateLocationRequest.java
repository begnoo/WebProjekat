package core.requests.locations;

public class CreateLocationRequest {
	private String latitude;
	private String longitude;
	private CreateAddressRequest address;
		
	public CreateLocationRequest() {
		super();
	}

	public CreateLocationRequest(String latitude, String longitude, CreateAddressRequest address) {
		super();
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}
	
	public String getLatitude() {
		return latitude;
	}
	
	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
	
	public String getLongitude() {
		return longitude;
	}
	
	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}
	
	public CreateAddressRequest getAddress() {
		return address;
	}
	
	public void setAddress(CreateAddressRequest address) {
		this.address = address;
	}
}
