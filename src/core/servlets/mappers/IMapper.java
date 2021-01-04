package core.servlets.mappers;

public interface IMapper {
	<T> T Map(T mappedObject, Object mappingObject);
	
	void addNestedMapping(Class<?> sourceType, Class<?> destinationType);

}
