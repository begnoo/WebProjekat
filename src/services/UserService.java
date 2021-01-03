package services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import core.domain.enums.TicketStatus;
import core.domain.enums.UserRole;
import core.domain.models.Buyer;
import core.domain.models.User;
import core.repository.IRepository;
import core.service.IUserService;

public class UserService extends CrudService<User> implements IUserService {

	public UserService(IRepository<User> repository) {
		super(repository);
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