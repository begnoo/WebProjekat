package repository.utils.loaders.list;

import java.util.List;

import core.domain.models.Manifestation;
import core.repository.IDependencyLoader;
import repository.DbContext;
import repository.utils.loaders.single.ManifestationDependencyLoader;

public class ManifestationsDependencyLoader implements IDependencyLoader<List<Manifestation>> {
	private ManifestationDependencyLoader manifestationDependencyLoader;
	
	public ManifestationsDependencyLoader(DbContext context)
	{
		manifestationDependencyLoader = new ManifestationDependencyLoader(context);
	}
	
	@Override
	public void load(List<Manifestation> manifestations) {
		for(Manifestation manifestation : manifestations)
		{
			manifestationDependencyLoader.load(manifestation);
		}	
	}
}
