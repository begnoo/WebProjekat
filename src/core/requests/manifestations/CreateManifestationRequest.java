package core.requests.manifestations;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.ManifestationType;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class CreateManifestationRequest {
	private String name;
	private ManifestationType type;
	private int seats;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime eventDate;
	private int regularTicketPrice;
	private UUID locationId;
	private UUID sellerId;
	
	public CreateManifestationRequest()
	{
		super();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public ManifestationType getType() {
		return type;
	}

	public void setType(ManifestationType type) {
		this.type = type;
	}

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	public LocalDateTime getEventDate() {
		return eventDate;
	}

	public void setEventDate(LocalDateTime eventDate) {
		this.eventDate = eventDate;
	}

	public int getRegularTicketPrice() {
		return regularTicketPrice;
	}

	public void setRegularTicketPrice(int regularTicketPrice) {
		this.regularTicketPrice = regularTicketPrice;
	}

	public UUID getLocationId() {
		return locationId;
	}

	public void setLocationId(UUID locationId) {
		this.locationId = locationId;
	}

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}
	
	

}
