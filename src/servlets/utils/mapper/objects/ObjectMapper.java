package servlets.utils.mapper.objects;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import core.servlets.mappers.IMapper;

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
	
	public <T> T Map(T mappedObject, Object mappingObject)
	{		
		try
		{			
			for(Field field : getAllFieldsFromClass(mappingObject.getClass()))
			{
				Method getterOfMappingObject = mappingObject.getClass().getMethod(getGetterNameForField(field.getType(), field.getName()));
				Object objectToSet = null;
				Method setterOfMappedObject = null;
				
				if(nestedMappings.containsKey(field.getType()))
				{
					setterOfMappedObject = mappedObject.getClass().getMethod(getSetterNameForField(field.getName()), nestedMappings.get(field.getType()));
					Method nestedMappedObjectGetter = mappedObject.getClass().getMethod(getGetterNameForField(field.getType(), field.getName()));
					objectToSet = Map(nestedMappedObjectGetter.invoke(mappedObject), getterOfMappingObject.invoke(mappingObject));
				} else {
					setterOfMappedObject = mappedObject.getClass().getMethod(getSetterNameForField(field.getName()), field.getType());
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
	
	private List<Field> getAllFieldsFromClass(Class<?> classType)
	{
		List<Field> fields = new ArrayList<Field>();
		Class<?> currentClass = classType;
		do
		{
			for(Field field : currentClass.getDeclaredFields())
			{
				fields.add(field);
			}
		
			currentClass = currentClass.getSuperclass();
		} while(currentClass != Object.class);
		
		return fields;
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
