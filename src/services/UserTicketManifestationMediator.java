package services;

import java.util.List;
import java.util.UUID;

import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Comment;
import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IBuyerTypeService;
import core.service.ICommentService;
import core.service.IManifestationService;
import core.service.ITicketService;
import core.service.IUserService;
import core.service.IUserTicketManifestationMediator;
import repository.CommentRepository;
import repository.DbContext;
import repository.ManifestationRepository;
import repository.Repository;
import repository.TicketRepository;
import repository.UserRepository;

public class UserTicketManifestationMediator implements IUserTicketManifestationMediator {

	private IUserService userService;
	private ITicketService ticketService;
	private IManifestationService manifestationService;
	
	public UserTicketManifestationMediator(DbContext context) {
		IRepository<User> userRepository = new UserRepository(context);
		IRepository<BuyerType> buyerTypeRepository = new Repository<BuyerType>(context, BuyerType.class);
		IRepository<Comment> commentRepository = new CommentRepository(context);
		IRepository<Manifestation> manifestationRepository = new ManifestationRepository(context);
		IRepository<Location> locationRepository = new Repository<Location>(context, Location.class);
		IRepository<Ticket> ticketRepository = new TicketRepository(context);
		
		IBuyerTypeService buyerTypeService = new BuyerTypeService(buyerTypeRepository);
		ICommentService commentService = new CommentService(commentRepository, manifestationRepository, userRepository);
		this.userService = new UserService(userRepository, buyerTypeService, commentService, this);
		this.ticketService = new TicketService(ticketRepository, this);
		this.manifestationService = new ManifestationService(manifestationRepository, locationRepository, commentService, this);
	}
	
	@Override
	public List<Ticket> readTicketsByBuyerId(UUID buyerId) {
		return ticketService.readByBuyerId(buyerId);
	}

	@Override
	public List<Ticket> readTicketsByManifestaionId(UUID manifestationId) {
		return ticketService.readByManifestationId(manifestationId);
	}

	@Override
	public Ticket deleteTicket(UUID ticketId) {
		return ticketService.delete(ticketId);
	}

	@Override
	public User readUser(UUID userId) {
		return userService.read(userId);
	}

	@Override
	public User updateBuyerPointsFor(Buyer buyer, int earnedPoints) {
		return userService.updateBuyerPointsFor(buyer, earnedPoints);
	}

	@Override
	public Manifestation updateNumberOfSeats(Manifestation manifestation, int seats) {
		return manifestationService.updateNumberOfSeats(manifestation, seats);
	}

	@Override
	public Manifestation readManifestation(UUID manifestationId) {
		return manifestationService.read(manifestationId);
	}

	@Override
	public List<Manifestation> readBySellerId(UUID sellerId) {
		return manifestationService.readBySellerId(sellerId);
	}

	@Override
	public Manifestation deleteManifestation(UUID manifestationId) {
		return manifestationService.delete(manifestationId);
	}
}
