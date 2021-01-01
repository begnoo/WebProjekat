package repository.utils.loaders.list;

import java.util.List;

import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.utils.loaders.single.SellerDependencyLoader;

public class SellersDependencyLoader implements IDependencyLoader<List<User>> {
	private SellerDependencyLoader sellerDependencyLoader;
	
	public SellersDependencyLoader(DbContext context)
	{
		sellerDependencyLoader = new SellerDependencyLoader(context);
	}
	
	@Override
	public void load(List<User> users) {
		for(User user : users)
		{
			sellerDependencyLoader.load(user);
		}
		
	}
}
