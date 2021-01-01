package repository.utils.loaders;

import core.domain.models.Location;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class ManifestationDependencyLoader implements IDependencyLoader {
	private DbSet<Manifestation> manifestations;
	private DbSet<Location> locations;
	private DbSet<User> users;
	
	@SuppressWarnings("unchecked")
	public ManifestationDependencyLoader(DbContext context)
	{
		manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
		locations = (DbSet<Location>) context.getSet(Location.class);
		users = (DbSet<User>) context.getSet(User.class);
	}
	
	@Override
	public void Load() {
		for(Manifestation manifestation : manifestations.read())
		{
			Location location = locations.read(manifestation.getLocationId());
			manifestation.setLocation(location);
		
			Seller seller = (Seller) users.read(manifestation.getSellerId());
			manifestation.setSeller(seller);
		}
		
	}

}
