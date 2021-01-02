package servlets.utils.mapper;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;

import core.servlets.IMapper;

public class ObjectMapper implements IMapper {
	private HashMap<Class<?>, Class<?>> nestedMappings;
	
	public ObjectMapper()
	{
		nestedMappings = new HashMap<Class<?>, Class<?>>();
	}
	
	public void addNestedMapping(Class<?> sourceType, Class<?> destinationType)
	{
		nestedMappings.put(sourceType, destinationType);
	}
	
	public <T> T Map(Class<T> targetedClassType, Object mappingObject)
	{
		try
		{
			Constructor<T> constructorOfTargetedClass = targetedClassType.getConstructor();
			T mappedObject = (T)constructorOfTargetedClass.newInstance();
			
			for(Field field : mappingObject.getClass().getDeclaredFields())
			{
				Method getterOfMappingObject = mappingObject.getClass().getMethod(getGetterNameForField(field.getType(), field.getName()));
				Object objectToSet = null;
				Method setterOfMappedObject = null;
				
				if(nestedMappings.containsKey(field.getType()))
				{
					setterOfMappedObject = targetedClassType.getMethod(getSetterNameForField(field.getName()), nestedMappings.get(field.getType()));
					objectToSet = Map(nestedMappings.get(field.getType()), getterOfMappingObject.invoke(mappingObject));
				} else {
					setterOfMappedObject = targetedClassType.getMethod(getSetterNameForField(field.getName()), field.getType());
					objectToSet = getterOfMappingObject.invoke(mappingObject);
				}
			
				setterOfMappedObject.invoke(mappedObject, objectToSet);
			}
			
			return mappedObject;

		}
		catch(Exception e) {
			System.out.println("Error while trying to map objects: " + e.getMessage());
		}
		
		return null;	
	}
	
	private String getGetterNameForField(Class<?> classType, String fieldName)
	{
		String prefix = (classType == boolean.class || classType == Boolean.class) ? "is" : "get";
		
		return prefix + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}
	
	private String getSetterNameForField(String fieldName)
	{
		return "set" + Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1);
	}
}
