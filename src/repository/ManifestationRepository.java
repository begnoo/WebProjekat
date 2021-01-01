package repository;

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

		return addedManifestation;
	}
	
	
	@Override
	public Manifestation update(Manifestation manifestationForUpdate) {
		Manifestation updatedManifestation = super.update(manifestationForUpdate);
		loadDependencies(updatedManifestation);
		
		return updatedManifestation;
	}
	
	private void loadDependencies(Manifestation comment) {
		IDependencyLoader<Manifestation> dependencyLoader = new ManifestationDependencyLoader(context);
		dependencyLoader.load(comment);
	}
}
