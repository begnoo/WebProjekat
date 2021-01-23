package servlets.utils.mapper.exceptions;

import javax.ws.rs.ext.Provider;

import core.exceptions.BadLogicException;

@Provider
public class BadLogicExceptionMapper extends ExceptionMapperBase<BadLogicException> {

	public BadLogicExceptionMapper() {
		super(400);
	}

}
