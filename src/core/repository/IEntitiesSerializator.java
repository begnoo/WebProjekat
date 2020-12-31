package core.repository;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import core.domain.models.BaseEntity;

public interface IEntitiesSerializator<T extends BaseEntity> {
	void Serialize(String fileName, List<T> entities) throws JsonGenerationException, JsonMappingException, IOException;

}
