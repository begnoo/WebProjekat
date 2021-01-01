package services;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketStatus;
import core.domain.models.Seller;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ITicketService;

public class TicketService extends CrudService<Ticket> implements ITicketService {

	IRepository<User> userRepository;

	public TicketService(IRepository<Ticket> repository, IRepository<User> userRepository) {
		super(repository);
		this.userRepository = userRepository;
	}

	@Override
	public List<Ticket> readByManifestationId(UUID manifestationId) {
		return repository.read().stream().filter(ticket -> manifestationId.equals(ticket.getManifestationId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readByBuyerId(UUID buyerId) {
		return repository.read().stream().filter(ticket -> buyerId.equals(ticket.getBuyerId()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Ticket> readReservedTicketsOfSellersManifestations(UUID sellerId) {
		Seller seller = (Seller) userRepository.read(sellerId);
		if (seller == null) {
			return null;
		}

		// TODO: Pogledati da li ovo moze nekako lepse da se odradi
		List<Ticket> tickets = new ArrayList<>();
		seller.getManifestations().forEach(manifestation -> tickets.addAll(readByManifestationId(manifestation.getId())
				.stream().filter(ticket -> ticket.getStatus() == TicketStatus.Reserved).collect(Collectors.toList())));

		return tickets;
	}

	@Override
	public Ticket create(Ticket entity) {
		entity.setStatus(TicketStatus.Reserved);
		return repository.create(entity);
	}

}
