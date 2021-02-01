package repository.utils.loaders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.repository.IDbSet;
import core.repository.ILazyLoader;
import repository.DbContext;
import repository.DbSet;

public class AttributesLazyLoader implements ILazyLoader {
	private DbContext context;
	
	public AttributesLazyLoader(DbContext context)
	{
		this.context = context;
	}
	
	public void loadDependencies(Object entity) {
		HashMap<String, Class<?>> dependencyNamesWithTypes = getDependencyNamesWithTypes(entity);
		
		for(String dependencyObjectName : dependencyNamesWithTypes.keySet()) {
			Class<?> dependentObjectClass = dependencyNamesWithTypes.get(dependencyObjectName);
			UUID idOfDependentObject = getIdOfDependentObject(entity, dependencyObjectName);

			DbSet<?> set = getDbSetForClass(dependentObjectClass);			
			Object dependentObject = readEntityFromDbSet(set, idOfDependentObject);
						
			setDependentObject(entity, dependentObject, dependencyObjectName, dependentObjectClass);
		}
	}
	
	private HashMap<String, Class<?>> getDependencyNamesWithTypes(Object entity) {
		List<Field> declaredFieldsInEntity = Arrays.asList(entity.getClass().getDeclaredFields());
		HashMap<String, Class<?>> dependencyNamesWithTypes = new HashMap<String, Class<?>>();
		
		for(Field declaredField : declaredFieldsInEntity) {
			Class<?> fieldType = declaredField.getType();
			String fieldName = declaredField.getName();
			String fieldNameForEntityId = fieldName + "Id";
				
			boolean shouldBeLazyLoaded = declaredFieldsInEntity.stream()
					.anyMatch(field -> field.getName().contentEquals(fieldNameForEntityId));
			
			if(!shouldBeLazyLoaded) {
				continue;
			}
			
			dependencyNamesWithTypes.put(fieldName, fieldType);
		}
		
		return dependencyNamesWithTypes;
	}
	
	private UUID getIdOfDependentObject(Object entity, String dependentObjectName) {
		String getterName = "get" + Character.toUpperCase(dependentObjectName.charAt(0)) + dependentObjectName.substring(1) + "Id";
		try {
			Class<?> entityType = entity.getClass();
			Method getter = entityType.getDeclaredMethod(getterName);
			
			return (UUID) getter.invoke(entity);
		} catch (Exception e) {
			System.out.println("Can't get getter method for: " + getterName);
		}
		
		return null;
	}
	
	private void setDependentObject(Object entity, Object dependentObject, String dependentObjectName, Class<?> dependentObjectClass) {
		Class<?> entityType = entity.getClass();
		String setterName = "set" + Character.toUpperCase(dependentObjectName.charAt(0)) + dependentObjectName.substring(1);
		try {
			Method setter = entityType.getDeclaredMethod(setterName, dependentObjectClass);
			setter.invoke(entity, dependentObject);
		} catch (Exception e) {
			System.out.println("Can't set dependent object with setter: " + setterName);
		}
	}
	
	private DbSet<?> getDbSetForClass(Class<?> classType)
	{
		Class<?> currentClass = classType;
		do {
			DbSet<?> set = (DbSet<?>) context.getSet(currentClass);
			if(set != null) {
				return set;
			}
		
			currentClass = currentClass.getSuperclass();
		} while(currentClass != Object.class);
		
		return null;
	}

	@SuppressWarnings("unchecked")
	private Object readEntityFromDbSet(IDbSet<?> set, UUID entityId) {
		try {
			HashMap<UUID, Object> entities;
			Field entitiesField;

			entitiesField = set.getClass().getDeclaredField("entities");
			entitiesField.setAccessible(true);
			entities = (HashMap<UUID, Object>) entitiesField.get(set);
			entitiesField.setAccessible(false);
			
			return entities.get(entityId);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
		}

		return null;
	}
}
