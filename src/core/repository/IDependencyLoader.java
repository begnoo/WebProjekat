package core.repository;

public interface IDependencyLoader<T> {

	void load(T entity);
}
