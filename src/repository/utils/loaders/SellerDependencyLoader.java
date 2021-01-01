package repository.utils.loaders;

import core.domain.enums.UserRole;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class SellerDependencyLoader implements IDependencyLoader{
	private DbSet<User> users;
	private DbSet<Manifestation> manifestations;

	@SuppressWarnings("unchecked")
	public SellerDependencyLoader(DbContext context)
	{
		users = (DbSet<User>) context.getSet(User.class);
		manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
	}
	
	@Override
	public void Load() {
		for(User user : users.read()) {
			if(user.getRole() != UserRole.Seller) {
				continue;
			}
			
			Seller seller = (Seller) user;
		
			LoadManifestationsForSeller(seller);
		}
		
	}

	private void LoadManifestationsForSeller(Seller seller) {
		for(Manifestation manifestation : manifestations.read()) {
			if(manifestation.getSellerId().equals(seller.getId())) {
				seller.getManifestations().add(manifestation);
			}
		}
	}
}
