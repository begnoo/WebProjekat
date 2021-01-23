package core.service;

import repository.DbContext;

public interface IServiceCreator<T> {
	T create(DbContext context);
}
