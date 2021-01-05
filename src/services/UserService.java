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
import core.service.IUserService;

public class UserService extends CrudService<User> implements IUserService {

	IRepository<BuyerType> buyerTypeRepository;
	
	public UserService(IRepository<User> repository, IRepository<BuyerType> buyerTypeRepository) {
		super(repository);
		this.buyerTypeRepository = buyerTypeRepository;
	}
	
	@Override
	public User create(User user) {
		
		if(!checkIfUsernameUnique(user.getUsername())) {
			return null;
		}
		
		if(user.getRole() == UserRole.Buyer) {
			((Buyer) user).setBuyerTypeId(getDefaultBuyerType().getId()); 
		}
		
		return repository.create(user);
	}
	
	private boolean checkIfUsernameUnique(String username) {
		return repository.read()
				.stream()
				.filter(user -> user.getUsername().equals(username))
				.collect(Collectors.toList()).size() == 0;
	}
	
	private BuyerType getDefaultBuyerType() {
		return buyerTypeRepository.read()
				.stream()
				.filter(buyerType -> buyerType.getName().equals("Default"))
				.collect(Collectors.toList()).get(0);
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

}