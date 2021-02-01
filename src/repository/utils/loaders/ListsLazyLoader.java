package repository.utils.loaders;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import core.repository.IDbSet;
import core.repository.ILazyLoader;
import repository.DbContext;
import repository.DbSet;

public class ListsLazyLoader implements ILazyLoader {
	private DbContext context;
	
	public ListsLazyLoader(DbContext context)
	{
		this.context = context;
	}
	
	public void loadDependencies(Object entity) {
		Class<?> entityClass = entity.getClass();
		UUID entityId = getIdOfObject(entity);
		
		List<Class<?>> dependencyTypes = getDependencyNamesWithTypes(entity);
		for(Class<?> dependencyClass : dependencyTypes) {
			DbSet<?> set = (DbSet<?>) context.getSet(dependencyClass);
			List<Object> dependentEntities = getAllDependentObjects(entityClass, entityId, set);
			
			setDependentList(entity, dependentEntities, dependencyClass);
		}
	}
	
	private UUID getIdOfObject(Object entity) {
		Class<?> currentClass = entity.getClass();
		do {
			try {
				Method getter = currentClass.getDeclaredMethod("getId");
				
				return (UUID) getter.invoke(entity);
			} catch (Exception e) {
			}
			currentClass = currentClass.getSuperclass();
		} while(currentClass != Object.class);
		
		return null;
	}
	
	private List<Class<?>> getDependencyNamesWithTypes(Object entity) {
		List<Field> declaredFieldsInEntity = Arrays.asList(entity.getClass().getDeclaredFields());
		List<Class<?>> dependencyTypes = new ArrayList<Class<?>>();
		
		for(Field declaredField : declaredFieldsInEntity) {
			Class<?> fieldType = declaredField.getType();

			if(!fieldType.getSimpleName().equals("List")) {
				continue;
			}
			
			try {
				String fieldName = declaredField.getName();
				String dependencyTypeClassName =  Character.toUpperCase(fieldName.charAt(0)) + fieldName.substring(1, fieldName.length() - 1);
				Class<?> dependencyTypeClass = Class.forName("core.domain.models." + dependencyTypeClassName);
				dependencyTypes.add(dependencyTypeClass);

			} catch (Exception e) {
				System.out.println(e.getMessage());
			}
		}
		
		return dependencyTypes;
	}
	
	private List<Object> getAllDependentObjects(Class<?> entityClass, UUID entityId, IDbSet<?> set) {		
		String getterForEntityId = "get" + entityClass.getSimpleName() + "Id";
		
		List<Object> dependentEntities = new ArrayList<Object>();
		List<Object> allEntitiesFromSet = getAllEntitiesFromDbSet(set);
		for(Object entity : allEntitiesFromSet) {
			try {
				Method getter = entity.getClass().getDeclaredMethod(getterForEntityId);
				UUID id = (UUID) getter.invoke(entity);
				if(id.equals(entityId)) {
					dependentEntities.add(entity);
				}
			} catch(Exception e) {
				System.out.println(e.getMessage());
			}
		}

		return dependentEntities;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private List<Object> getAllEntitiesFromDbSet(IDbSet<?> set) {
		List<Object> entities = new ArrayList<Object>();
		
		try {
			Field entitiesField;

			entitiesField = set.getClass().getDeclaredField("entities");
			entitiesField.setAccessible(true);
			entities = new ArrayList(((HashMap<UUID, Object>) entitiesField.get(set)).values());
			entitiesField.setAccessible(false);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		
		return entities;
	}
	
	public void setDependentList(Object entity, List<Object> dependentObjects, Class<?> dependentClass) {
		Class<?> entityType = entity.getClass();
		String setterName = "set" + dependentClass.getSimpleName() + "s";
		try {
			Method setter = entityType.getDeclaredMethod(setterName, List.class);
			setter.invoke(entity, dependentObjects);
		} catch (Exception e) {
			System.out.println("Can't set dependent object with setter: " + setterName);
		}

	}
}
