package repository.utils.loaders;

import core.repository.ILazyLoader;
import repository.DbContext;

public class LazyLoader implements ILazyLoader {
	private ILazyLoader attributeLazyLoader;
	private ILazyLoader listsLazyLoader;
	
	public LazyLoader(DbContext context)
	{
		attributeLazyLoader = new AttributesLazyLoader(context);
		listsLazyLoader = new ListsLazyLoader(context);
	}
	
	@Override
	public void loadDependencies(Object entity) {
		attributeLazyLoader.loadDependencies(entity);
		listsLazyLoader.loadDependencies(entity);	
	}
}
