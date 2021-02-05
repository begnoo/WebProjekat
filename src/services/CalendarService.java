package services;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.dto.BuyerTicketsForCalendar;
import core.domain.enums.TicketStatus;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.exceptions.BadLogicException;
import core.service.ICalendarService;
import core.service.ITicketService;

public class CalendarService implements ICalendarService {
	
	private ITicketService ticketService;
	
	public CalendarService(ITicketService ticketService)
	{
		this.ticketService = ticketService;
	}
	
	@Override
	public BuyerTicketsForCalendar getBuyerTicketsForDate(UUID buyerId, LocalDate wantedDate) {
		if(wantedDate == null) {
			throw new BadLogicException("Wanted date can not be null.");
		}
		
		List<Ticket> buyerTickets = ticketService.readByBuyerId(buyerId);
		List<Ticket> buyerTicketsOnWantedDate = buyerTickets.stream()
				.filter(ticket -> ticket.getStatus() == TicketStatus.Reserved)
				.filter(ticket -> isOnWantedDate(ticket, wantedDate))
				.collect(Collectors.toList());
		
		Map<UUID, Location> locationsOfTickets = buyerTicketsOnWantedDate.stream()
				.collect(Collectors.toMap(
						ticket -> ticket.getManifestation().getLocationId(),
						ticket -> ticket.getManifestation().getLocation(),
						(ticket1, ticket2) -> ticket1));
		
		return new BuyerTicketsForCalendar(locationsOfTickets, buyerTicketsOnWantedDate);
	}
	
	private boolean isOnWantedDate(Ticket ticket, LocalDate wantedDate)
	{		
		Manifestation manifestation = ticket.getManifestation(); 
		LocalDate startDate = manifestation.getEventDate().toLocalDate();
		LocalDate endDate = manifestation.getEventEndDate().toLocalDate();
		
		return startDate.compareTo(wantedDate) <= 0 && wantedDate.compareTo(endDate) <= 0;
	}
}
