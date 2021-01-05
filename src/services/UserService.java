package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.TicketStatus;
import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.BuyerType;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IBuyerTypeService;
import core.service.IUserService;

public class UserService extends CrudService<User> implements IUserService {

	IBuyerTypeService buyerTypeService;
	
	public UserService(IRepository<User> repository, IBuyerTypeService buyerTypeService) {
		super(repository);
		this.buyerTypeService = buyerTypeService;
	}
	
	@Override
	public User create(User user) {
		
		if(!isUsernameUnique(user.getUsername())) {
			return null;
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
			return null;
		}
		
		if(!user.getPassword().equals(currentPassword))
		{
			return null; // TODO: THROW BAD LOGIC EXCEPTION
		}
		
		user.setPassword(newPassword);
		return update(user);
	}

	@Override
	public User blockUser(UUID userId) {
		User user = repository.read(userId);
		if(user == null || user.getRole() == UserRole.Administrator) {
			return null;
		}
		
		user.setActive(false);
		return update(user);
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