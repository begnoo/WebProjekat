package core.responses.tickets;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import core.domain.models.Location;

public class BuyerTicketsForCalendarResponse {
	private Map<UUID, Location> locationsOfTickets;
	private List<WholeTicketObjectResponse> ticketsOnDate;

	public BuyerTicketsForCalendarResponse() {
		super();
	}

	public BuyerTicketsForCalendarResponse(Map<UUID, Location> locationsOfTickets, List<WholeTicketObjectResponse> ticketsOnDate) {
		super();
		this.locationsOfTickets = locationsOfTickets;
		this.ticketsOnDate = ticketsOnDate;
	}

	public Map<UUID, Location> getLocationsOfTickets() {
		return locationsOfTickets;
	}

	public void setLocationsOfTickets(Map<UUID, Location> locationsOfTickets) {
		this.locationsOfTickets = locationsOfTickets;
	}

	public List<WholeTicketObjectResponse> getTicketsOnDate() {
		return ticketsOnDate;
	}

	public void setTicketsOnDate(List<WholeTicketObjectResponse> ticketsOnDate) {
		this.ticketsOnDate = ticketsOnDate;
	}

}
