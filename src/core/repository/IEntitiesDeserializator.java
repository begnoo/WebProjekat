package core.repository;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;

import core.domain.models.BaseEntity;

public interface IEntitiesDeserializator<T extends BaseEntity> {
	List<T> Deserialize(String fileName) throws JsonParseException, JsonMappingException, IOException;
}
