package core.requests.tickets;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class CreateTicketRequest {

	private String uniqueId;
	private UUID manifestationId;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime manifestationDate;
	private int price;
	private UUID buyerId;
	private TicketStatus status;
	private TicketType type;

	public CreateTicketRequest() {
		super();
	}

	public String getUniqueId() {
		return uniqueId;
	}

	public void setUniqueId(String uniqueId) {
		this.uniqueId = uniqueId;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
	}

	public LocalDateTime getManifestationDate() {
		return manifestationDate;
	}

	public void setManifestationDate(LocalDateTime manifestationDate) {
		this.manifestationDate = manifestationDate;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public UUID getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}

	public TicketStatus getStatus() {
		return status;
	}

	public void setStatus(TicketStatus status) {
		this.status = status;
	}

	public TicketType getType() {
		return type;
	}

	public void setType(TicketType type) {
		this.type = type;
	}

}
