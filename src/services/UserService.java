package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.TicketStatus;
import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.Comment;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.Ticket;
import core.domain.models.User;
import core.exceptions.BadLogicException;
import core.exceptions.MissingEntityException;
import core.repository.IRepository;
import core.service.IBuyerTypeService;
import core.service.ICommentService;
import core.service.IUserService;
import core.service.IUserTicketManifestationMediator;

public class UserService extends CrudService<User> implements IUserService {

	private IBuyerTypeService buyerTypeService;
	private ICommentService commentService;
	private IUserTicketManifestationMediator mediator;
	
	public UserService(IRepository<User> repository, IBuyerTypeService buyerTypeService, ICommentService commentService, IUserTicketManifestationMediator mediator) {
		super(repository);
		this.buyerTypeService = buyerTypeService;
		this.commentService = commentService;
		this.mediator = mediator;
	}
	
	@Override
	public User create(User user) {
		if(!isUsernameUnique(user.getUsername())) {
			throw new BadLogicException("Username is taken.");
		}
		
		if(user.getRole() == UserRole.Buyer) {
			((Buyer) user).setBuyerTypeId(buyerTypeService.getDefaultBuyerType().getId()); 
		}
		
		return repository.create(user);
	}
	
	private boolean isUsernameUnique(String username) {
		return repository.read()
				.stream()
				.filter(user -> user.getUsername().equals(username))
				.collect(Collectors.toList()).size() == 0;
	}
	
	@Override
	public User changePassword(UUID userId, String newPassword, String currentPassword) {
		User user = repository.read(userId);
		if(user == null) {
			throw new MissingEntityException(String.format("User with id = %s does not exists.", userId));
		}
		
		if(!user.getPassword().equals(currentPassword))
		{
			throw new BadLogicException("Given password does not match current password.");
		}
		
		user.setPassword(newPassword);
		return update(user);
	}

	@Override
	public User blockUser(UUID userId) {
		User user = repository.read(userId);
		if(user == null) {
			throw new MissingEntityException(String.format("User with id = %s does not exists.", userId));
		}
		if(user.getRole() == UserRole.Administrator) {
			throw new BadLogicException("Administrator can not be blocked.");
		}
		
		if(user.getRole() == UserRole.Buyer) {
			blockBuyer((Buyer)user);
		}
		
		if(user.getRole() == UserRole.Seller) {
			blockSeller((Seller)user);
		}
				
		return super.delete(userId);
	}
	
	private void blockBuyer(Buyer buyer) {
		UUID buyerId = buyer.getId();
		List<Comment> buyerComments = commentService.readByBuyerId(buyerId);
		for(Comment comment : buyerComments) {
			commentService.delete(comment.getId());
		}
		
		List<Ticket> buyerTickets = mediator.readTicketsByBuyerId(buyerId);
		for(Ticket ticket : buyerTickets) {
			mediator.deleteTicket(ticket.getId());
		}

	}
	
	private void blockSeller(Seller seller) {
		for(Manifestation manifestation : seller.getManifestations()) {
			mediator.deleteManifestation(manifestation.getId());
		}
	}

	@Override
	public List<User> readByUserRole(UserRole userRole) {
		return repository.read()
				.stream()
				.filter(user -> user.getRole() == userRole)
				.collect(Collectors.toList());
	}

	@Override
	public List<User> readDistrustfulBuyers() {
		List<User> distrustfulBuyers = readByUserRole(UserRole.Buyer)
				.stream()
				.filter(user -> isBuyerDistrustful((Buyer) user))
				.collect(Collectors.toList());
		
		return distrustfulBuyers;
	}
	
	private boolean isBuyerDistrustful(Buyer buyer)
	{
		int numberOfCanceledTicketsInLastMonth = buyer.getTickets().stream()
				.filter(ticket -> ticket.getStatus() == TicketStatus.Canceled)
				.filter(ticket -> checkIfInLastMonth(ticket.getManifestationDate()))
				.collect(Collectors.toList())
				.size();
		
		return numberOfCanceledTicketsInLastMonth > 5;
	}
	
	private boolean checkIfInLastMonth(LocalDateTime eventDate) {
		return eventDate.isAfter(LocalDateTime.now().minusMonths(1));
	}

	@Override
	public User updateBuyerPointsFor(Buyer buyer, int additionalPoints) {
		int newBuyerPoints = calculateNewPoints(buyer.getPoints(), additionalPoints);
		buyer.setPoints(newBuyerPoints);
		
		BuyerType buyerTypeForNewPoints = buyerTypeService.findAppropriateTypeForPoints(buyer.getPoints());
		buyer.setBuyerTypeId(buyerTypeForNewPoints.getId());
		
		return repository.update(buyer);

	}
	
	private int calculateNewPoints(int currentPoints, int additionalPoints) {
		int newPoints = currentPoints + additionalPoints;
		
		return Math.max(0, newPoints);
	}
}