package core.domain.dto;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.UUID;

import core.domain.enums.TicketType;

public class TicketOrder {

	private UUID manifestationId;
	private LocalDateTime manifestationDate;
	private UUID buyerId;

	private HashMap<TicketType, Integer> numberOfOrderedTicketsMap;

	public TicketOrder() {
		super();
	}

	public TicketOrder(UUID manifestationId, LocalDateTime manifestationDate, UUID buyerId,
			HashMap<TicketType, Integer> numberOfOrderedTicketsMap) {
		super();
		this.manifestationId = manifestationId;
		this.manifestationDate = manifestationDate;
		this.buyerId = buyerId;
		this.numberOfOrderedTicketsMap = numberOfOrderedTicketsMap;
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

	public UUID getBuyerId() {
		return buyerId;
	}

	public void setBuyerId(UUID buyerId) {
		this.buyerId = buyerId;
	}

	public HashMap<TicketType, Integer> getNumberOfOrderedTicketsMap() {
		return numberOfOrderedTicketsMap;
	}

	public void setNumberOfOrderedTicketsMap(HashMap<TicketType, Integer> numberOfOrderedTicketsMap) {
		this.numberOfOrderedTicketsMap = numberOfOrderedTicketsMap;
	}

}
