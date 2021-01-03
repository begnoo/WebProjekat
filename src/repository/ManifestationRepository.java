package repository;

import java.util.UUID;

import core.domain.models.Manifestation;
import core.repository.IDependencyLoader;
import repository.utils.loaders.single.ManifestationDependencyLoader;

public class ManifestationRepository extends Repository<Manifestation> {
	public ManifestationRepository(DbContext context)
	{
		super(context, Manifestation.class);
	}
	
	@Override
	public Manifestation create(Manifestation manifestation) {
		Manifestation addedManifestation = super.create(manifestation);
		loadDependencies(addedManifestation);
		addedManifestation.getSeller().getManifestations().add(addedManifestation);

		return addedManifestation;
	}
	
	
	@Override
	public Manifestation update(Manifestation manifestationForUpdate) {
		Manifestation updatedManifestation = super.update(manifestationForUpdate);
		loadDependencies(updatedManifestation);
		
		return updatedManifestation;
	}
	
	@Override
	public boolean delete(UUID entityID) {
		Manifestation manifestationForDelition = entities.read(entityID);
		boolean isDeleted = entities.remove(entityID);
		
		if(isDeleted == true) {
			manifestationForDelition.getSeller().getManifestations().remove(manifestationForDelition);
			entities.save();
		}
		
		return isDeleted;
	}
	
	private void loadDependencies(Manifestation comment) {
		IDependencyLoader<Manifestation> dependencyLoader = new ManifestationDependencyLoader(context);
		dependencyLoader.load(comment);
	}
}
