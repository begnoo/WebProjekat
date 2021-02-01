package core.repository;

public interface ILazyLoader {
	public void loadDependencies(Object entity);
}
