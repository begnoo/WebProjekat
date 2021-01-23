package repository;

import java.util.UUID;

import core.domain.models.Manifestation;
import core.exceptions.MissingEntityException;
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
	public Manifestation delete(UUID manifestationId) {
		Manifestation deletedManifestation = entities.remove(manifestationId);
		
		if(deletedManifestation == null) {
			throw new MissingEntityException("Manifestation does not exist");
		}
		
		deletedManifestation.getSeller().getManifestations().remove(deletedManifestation);
		entities.save();

		return deletedManifestation;
	}
	
	private void loadDependencies(Manifestation comment) {
		IDependencyLoader<Manifestation> dependencyLoader = new ManifestationDependencyLoader(context);
		dependencyLoader.load(comment);
	}
}
