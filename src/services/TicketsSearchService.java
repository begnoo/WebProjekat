package services;

import java.util.List;

import core.domain.dto.TicketsSearchParamethers;
import core.domain.models.Ticket;
import core.repository.IDbSetStream;
import core.repository.IRepository;
import core.service.IAdvanceSearchService;

public class TicketsSearchService implements IAdvanceSearchService<Ticket, TicketsSearchParamethers> {

	private IRepository<Ticket> repository;
	
	public TicketsSearchService(IRepository<Ticket> repository)
	{
		this.repository = repository;
	}
	
	@Override
	public List<Ticket> search(TicketsSearchParamethers searchParamethers) {
		IDbSetStream<Ticket> stream = repository.getStream()
				.filter(ticket -> ticket.getBuyerId().equals(searchParamethers.getBuyerId()))
				.filter(ticket -> ticket.getManifestation().getName().toLowerCase().contains(searchParamethers.getManifestationName().toLowerCase()))
				.filter(ticket -> searchParamethers.getDateFrom() == null || ticket.getManifestation().getEventDate().compareTo(searchParamethers.getDateFrom()) >= 0)
				.filter(ticket -> searchParamethers.getDateTo() == null || ticket.getManifestation().getEventDate().compareTo(searchParamethers.getDateTo()) <= 0)
				.filter(ticket -> ticket.getPrice() >= searchParamethers.getPriceFrom())
				.filter(ticket -> ticket.getPrice() <= searchParamethers.getPriceTo())
				.filter(ticket -> searchParamethers.getType().isBlank() || ticket.getType().toString().equals(searchParamethers.getType()))
				.filter(ticket -> searchParamethers.getStatus().isBlank() || ticket.getStatus().toString().equals(searchParamethers.getStatus()));
		
		return stream.collect();
	}

}
