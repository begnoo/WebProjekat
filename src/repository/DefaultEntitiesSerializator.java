package repository;

import java.io.File;
import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import core.domain.models.BaseEntity;
import core.repository.IEntitiesSerializator;

public class DefaultEntitiesSerializator<T extends BaseEntity> implements IEntitiesSerializator<T> {

	private ObjectMapper mapper;

	public DefaultEntitiesSerializator()
	{
		this.mapper = new ObjectMapper();
		this.mapper.enable(SerializationFeature.INDENT_OUTPUT);
	}

	@Override
	public void Serialize(String fileName, List<T> entities) throws JsonGenerationException, JsonMappingException, IOException {
		mapper.writeValue(new File(fileName), entities);
	}

}
