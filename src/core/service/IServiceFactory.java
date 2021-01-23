package core.service;

import repository.DbContext;

public interface IServiceFactory {
	Object getService(Class<?> serviceClass, DbContext context);
}
