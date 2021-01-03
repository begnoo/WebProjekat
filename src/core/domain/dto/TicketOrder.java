package core.domain.dto;

import java.util.HashMap;
import java.util.UUID;

import core.domain.enums.TicketType;

public class TicketOrder {

	private UUID manifestationId;
	private UUID buyerId;

	private HashMap<TicketType, Integer> numberOfOrderedTicketsMap;

	public TicketOrder() {
		super();
	}

	public TicketOrder(UUID manifestationId, UUID buyerId, HashMap<TicketType, Integer> numberOfOrderedTicketsMap) {
		super();
		this.manifestationId = manifestationId;
		this.buyerId = buyerId;
		this.numberOfOrderedTicketsMap = numberOfOrderedTicketsMap;
	}

	public UUID getManifestationId() {
		return manifestationId;
	}

	public void setManifestationId(UUID manifestationId) {
		this.manifestationId = manifestationId;
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
