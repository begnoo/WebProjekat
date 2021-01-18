package services;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.domain.dto.TicketOrder;
import core.domain.enums.TicketStatus;
import core.domain.enums.TicketType;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.ITicketOrderService;
import core.service.ITicketService;

public class TicketOrderService implements ITicketOrderService {
	private ITicketService ticketService;
	private IRepository<User> userRepository;
	private IRepository<Manifestation> manifestationRepository;
	private IRepository<BuyerType> buyerTypeRepository;

	public TicketOrderService(ITicketService ticketService, IRepository<User> userRepository,
			IRepository<Manifestation> manifestationRepository, IRepository<BuyerType> buyerTypeRepository) {
		this.ticketService = ticketService;
		this.userRepository = userRepository;
		this.manifestationRepository = manifestationRepository;
		this.buyerTypeRepository = buyerTypeRepository;
	}

	@Override
	public List<Ticket> createTicketsFromOrder(TicketOrder ticketOrder) {
		List<Ticket> tickets = new ArrayList<>();

		Buyer buyer = (Buyer) userRepository.read(ticketOrder.getBuyerId());

		Manifestation manifestation = manifestationRepository.read(ticketOrder.getManifestationId());
		if (manifestation == null || manifestation.getEventDate().isBefore(LocalDateTime.now())) {
			return tickets;
		}

		int numberOfAllTickets = ticketOrder.getNumberOfOrderedTicketsMap().values().stream().reduce(0, Integer::sum);

		if (numberOfAllTickets <= manifestation.getSeats()) {
			ticketOrder.getNumberOfOrderedTicketsMap().forEach((ticketType, numberOfTickets) -> {
				for (int i = 0; i < numberOfTickets; ++i) {

					int price = getPriceOfTicket(manifestation.getRegularTicketPrice(), ticketType);
					int priceWithDiscount = getPriceOfTicketWithDiscount(price, buyer.getType());

					Ticket ticket = new Ticket("", manifestation.getId(), manifestation, manifestation.getEventDate(),
							priceWithDiscount, buyer.getId(), buyer, TicketStatus.Reserved, ticketType);

					tickets.add(ticketService.create(ticket));
				}
			});
		}

		return tickets;
	}

	private int getPriceOfTicket(int regularPrice, TicketType ticketType) {
		int typeModifier = ticketType.getModifier();
		return regularPrice * typeModifier;
	}

	private int getPriceOfTicketWithDiscount(int price, BuyerType buyerType) {
		double discount = (100.0 - buyerType.getDiscount()) / 100;
		int priceWithDiscount = (int) (price * discount);
		return priceWithDiscount;
	}

	public HashMap<TicketType, Integer> getTicketPrices(int regularPrice, UUID buyerTypeId) {
		HashMap<TicketType, Integer> ticketPricesMap = new HashMap<TicketType, Integer>();
		BuyerType buyerType = buyerTypeRepository.read(buyerTypeId);
		ticketPricesMap.put(TicketType.Vip,
				getPriceOfTicketWithDiscount(getPriceOfTicket(regularPrice, TicketType.Vip), buyerType));
		ticketPricesMap.put(TicketType.FanPit,
				getPriceOfTicketWithDiscount(getPriceOfTicket(regularPrice, TicketType.FanPit), buyerType));
		ticketPricesMap.put(TicketType.Regular,
				getPriceOfTicketWithDiscount(getPriceOfTicket(regularPrice, TicketType.Regular), buyerType));
		return ticketPricesMap;
	}
}
