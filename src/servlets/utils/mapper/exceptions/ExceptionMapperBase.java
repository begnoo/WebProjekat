package servlets.utils.mapper.exceptions;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class ExceptionMapperBase<T extends Exception> implements ExceptionMapper<T> {

    protected int statusCode;
    
    public ExceptionMapperBase(int statusCode)
    {
    	this.statusCode = statusCode;
    }
    
	@Override
    public Response toResponse(T exception){
        return Response.status(statusCode)
        		.entity("{ \"errorMessage\": \"" + exception.getMessage() + "\" } ")
        		.type(getAcceptType())
        		.build();
    }

    private String getAcceptType(){
    	return "application/json";
    }
}
