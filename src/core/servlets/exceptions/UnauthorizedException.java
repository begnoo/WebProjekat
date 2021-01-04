package core.servlets.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class UnauthorizedException extends WebApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UnauthorizedException() {
        super(Response.status(Status.UNAUTHORIZED)
       		 .build());
    }

    public UnauthorizedException(String message) {
        super(Response.status(Status.UNAUTHORIZED)
       		 .entity("{ \"errorMessage\": \"" + message + "\" } ")
       		 .type("application/json")
       		 .build());
    }

}