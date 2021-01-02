package core.requests.locations;

import java.util.UUID;

public class UpdateLocationRequest {
	private UUID id;
	private String latitude;
	private String longitude;
	private UpdateAddressRequest address;
	
	public UpdateLocationRequest()
	{
		
	}
	
	public UpdateLocationRequest(UUID id, String latitude, String longitude, UpdateAddressRequest address) {
		super();
		this.id = id;
		this.latitude = latitude;
		this.longitude = longitude;
		this.address = address;
	}

	public UUID getId() {
		return id;
	}
	
	public void setId(UUID id) {
		this.id = id;
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
	
	public UpdateAddressRequest getAddress() {
		return address;
	}
	
	public void setAddress(UpdateAddressRequest address) {
		this.address = address;
	}

}
