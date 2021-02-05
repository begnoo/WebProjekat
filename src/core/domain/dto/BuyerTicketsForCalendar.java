package core.domain.dto;

import java.util.List;
import java.util.Map;
import java.util.UUID;

import core.domain.models.Location;
import core.domain.models.Ticket;

public class BuyerTicketsForCalendar {
	private Map<UUID, Location> locationsOfTickets;
	private List<Ticket> ticketsOnDate;

	public BuyerTicketsForCalendar() {
		super();
	}

	public BuyerTicketsForCalendar(Map<UUID, Location> locationsOfTickets, List<Ticket> ticketsOnDate) {
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

	public List<Ticket> getTicketsOnDate() {
		return ticketsOnDate;
	}

	public void setTicketsOnDate(List<Ticket> ticketsOnDate) {
		this.ticketsOnDate = ticketsOnDate;
	}

}
