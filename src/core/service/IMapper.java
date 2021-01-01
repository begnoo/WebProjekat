package core.service;

public interface IMapper {
	<T> T Map(Class<T> targetedClassType, Object mappingObject);
}
