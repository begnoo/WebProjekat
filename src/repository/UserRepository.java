package repository;

import core.domain.models.User;
import core.repository.ILazyLoader;
import repository.utils.loaders.LazyLoader;

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
		ILazyLoader loader = new LazyLoader(context);
		loader.loadDependencies(user);
	}
}
