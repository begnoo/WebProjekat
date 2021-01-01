package repository.utils.loaders.list;

import java.util.List;

import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.utils.loaders.single.BuyerDependencyLoader;

public class BuyersDependencyLoader implements IDependencyLoader<List<User>> {
	private BuyerDependencyLoader buyerDependencyLoader;
	
	public BuyersDependencyLoader(DbContext context)
	{
		buyerDependencyLoader = new BuyerDependencyLoader(context);
	}

	@Override
	public void load(List<User> users) {
		for(User user : users)
		{
			buyerDependencyLoader.load(user);
		}
	}
}