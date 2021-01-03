package core.responses.manifestations;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.ManifestationType;
import core.domain.models.Location;
import core.domain.models.Seller;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class WholeManifestationObjectResponse {
	private UUID id;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime createdAt;
	private boolean active;
	private String name;
	private ManifestationType type;
	private int seats;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime eventDate;
	private int regularTicketPrice;
	private boolean status;
	private UUID locationId;
	private Location location;
	private UUID sellerId;
	private Seller seller;

	public WholeManifestationObjectResponse() {
		super();
	}

	public WholeManifestationObjectResponse(UUID id, LocalDateTime createdAt, boolean active, String name,
			ManifestationType type, int seats, LocalDateTime eventDate, int regularTicketPrice, boolean status,
			UUID locationId, Location location, UUID sellerId, Seller seller) {
		super();
		this.id = id;
		this.createdAt = createdAt;
		this.active = active;
		this.name = name;
		this.type = type;
		this.seats = seats;
		this.eventDate = eventDate;
		this.regularTicketPrice = regularTicketPrice;
		this.status = status;
		this.locationId = locationId;
		this.location = location;
		this.sellerId = sellerId;
		this.seller = seller;
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
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

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public UUID getLocationId() {
		return locationId;
	}

	public void setLocationId(UUID locationId) {
		this.locationId = locationId;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public UUID getSellerId() {
		return sellerId;
	}

	public void setSellerId(UUID sellerId) {
		this.sellerId = sellerId;
	}

	public Seller getSeller() {
		return seller;
	}

	public void setSeller(Seller seller) {
		this.seller = seller;
	}
}