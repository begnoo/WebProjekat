package repository.utils.loaders.single;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class ManifestationDependencyLoader implements IDependencyLoader<Manifestation> {
	private DbSet<Location> locations;
	private DbSet<User> users;
	
	@SuppressWarnings("unchecked")
	public ManifestationDependencyLoader(DbContext context)
	{
		locations = (DbSet<Location>) context.getSet(Location.class);
		users = (DbSet<User>) context.getSet(User.class);
	}
	
	@Override
	public void load(Manifestation manifestation) {
		Location location = locations.read(manifestation.getLocationId());
		manifestation.setLocation(location);
	
		Seller seller = (Seller) users.read(manifestation.getSellerId());
		manifestation.setSeller(seller);
	}
}