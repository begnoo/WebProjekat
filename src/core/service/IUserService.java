package core.service;

import java.util.List;
import java.util.UUID;

import core.domain.enums.UserRole;
import core.domain.models.User;

public interface IUserService extends ICrudService<User>{
	User changePassword(UUID userId, String newPassword, String currentPassword);
	User blockUser(UUID userId);
	List<User> readByUserRole(UserRole role);
	List<User> readDistrustfulBuyers();
}
