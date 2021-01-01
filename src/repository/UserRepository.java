package repository;

import core.domain.enums.UserRole;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.utils.loaders.single.BuyerDependencyLoader;
import repository.utils.loaders.single.SellerDependencyLoader;

public class UserRepository extends Repository<User> {
	public UserRepository(DbContext context)
	{
		super(context, User.class);
	}
	
	@Override
	public User create(User user) {
		User addedUser = super.create(user);
		loadDependencies(addedUser);

		return addedUser;
	}
	
	
	@Override
	public User update(User userForUpdate) {
		User updatedUser = super.update(userForUpdate);
		loadDependencies(updatedUser);
		
		return updatedUser;
	}
	
	private void loadDependencies(User user) {
		IDependencyLoader<User> dependencyLoader = null;
		if(user.getRole() == UserRole.Buyer) {
			dependencyLoader = new BuyerDependencyLoader(context);
		} else if (user.getRole() == UserRole.Seller) {
			dependencyLoader = new SellerDependencyLoader(context);
		}
		
		if(dependencyLoader != null) {
			dependencyLoader.load(user);
		}
	}
}
