package repository.utils.deserializators;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import core.domain.models.BaseEntity;
import core.repository.IEntitiesDeserializator;

public class DefaultEntitiesDeserializator<T extends BaseEntity> implements IEntitiesDeserializator<T> {
	private Class<T> classType;
	private ObjectMapper mapper;

	public DefaultEntitiesDeserializator(Class<T> classType)
	{
		this.classType = classType;
		this.mapper = new ObjectMapper();

	}
	
	@Override
	public List<T> Deserialize(String fileName) throws JsonParseException, JsonMappingException, IOException {
		CollectionType typeReference = TypeFactory.defaultInstance().constructCollectionType(List.class, classType);
		List<T> listOfEntities = mapper.readValue(new File(fileName), typeReference);

		return listOfEntities;
	}

}
