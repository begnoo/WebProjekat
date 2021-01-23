package servlets.utils.mapper.exceptions;

import javax.ws.rs.ext.Provider;

import core.exceptions.MissingEntityException;

@Provider
public class MissingEntityExceptionMapper extends ExceptionMapperBase<MissingEntityException> {

	public MissingEntityExceptionMapper() {
		super(404);
	}

}
