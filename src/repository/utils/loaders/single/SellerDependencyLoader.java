package repository.utils.loaders.single;

import core.domain.enums.UserRole;
import core.domain.models.Manifestation;
import core.domain.models.Seller;
import core.domain.models.User;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.DbSet;

public class SellerDependencyLoader implements IDependencyLoader<User> {
	private DbSet<Manifestation> manifestations;

	@SuppressWarnings("unchecked")
	public SellerDependencyLoader(DbContext context)
	{
		manifestations = (DbSet<Manifestation>) context.getSet(Manifestation.class);
	}
	
	@Override
	public void load(User user) {
		if(user.getRole() != UserRole.Seller) {
			return;
		}
		
		Seller seller = (Seller) user;
	
		LoadManifestationsForSeller(seller);
	}

	private void LoadManifestationsForSeller(Seller seller) {
		seller.getManifestations().clear();
		for(Manifestation manifestation : manifestations.read()) {
			if(manifestation.getSellerId().equals(seller.getId())) {
				seller.getManifestations().add(manifestation);
			}
		}
	}
}
