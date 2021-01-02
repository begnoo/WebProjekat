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
		List<User> buyers = readByUserRole(UserRole.Buyer)
				.stream()
				.filter(user -> {
					Buyer buyer = (Buyer) user;
					return buyer.getTickets().stream()
					.filter(ticket -> checkIfInLastMonth(ticket.getManifestationDate()))
					.filter(ticket -> ticket.getStatus() == TicketStatus.Canceled)
					.collect(Collectors.toList())
					.size() > 5;
				})
				.collect(Collectors.toList());
		return null;
	}
	
	private boolean checkIfInLastMonth(LocalDateTime eventDate) {
		return eventDate.isAfter(LocalDateTime.now().minusMonths(1));
	}

}
