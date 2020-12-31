package core.domain.models;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.utils.JsonDateDeserializer;
import core.utils.JsonDateSerializer;

public class Ticket extends BaseEntity {
	private String uniqueId;
	private UUID manifestationId;
	private Manifestation manifestation;
	@JsonSerialize(using = JsonDateSerializer.class)
	@JsonDeserialize(using = JsonDateDeserializer.class)
	private LocalDateTime manifestationDate;
	private int price;
	private UUID buyerId;
	private Buyer buyer;
	private TicketStatus status;
	private TicketType type;
	
	public Ticket()
	{
		super();
	}

	public Ticket(String uniqueId, UUID manifestationId, Manifestation manifestation, LocalDateTime manifestationDate,
			int price, UUID buyerId, Buyer buyer, TicketStatus status, TicketType type) {
		super();
		this.uniqueId = uniqueId;
		this.manifestationId = manifestationId;
		this.manifestation = manifestation;
		this.manifestationDate = manifestationDate;
		this.price = price;
		this.buyerId = buyerId;
		this.buyer = buyer;
		this.status = status;
		this.type = type;
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

	public Manifestation getManifestation() {
		return manifestation;
	}

	public void setManifestation(Manifestation manifestation) {
		this.manifestation = manifestation;
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

	public Buyer getBuyer() {
		return buyer;
	}

	public void setBuyer(Buyer buyer) {
		this.buyer = buyer;
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
