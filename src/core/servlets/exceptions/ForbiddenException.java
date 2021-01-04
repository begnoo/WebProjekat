package core.servlets.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class ForbiddenException extends WebApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ForbiddenException() {
        super(Response.status(Status.FORBIDDEN)
       		 .build());
    }

    public ForbiddenException(String message) {
        super(Response.status(Status.FORBIDDEN)
       		 .entity("{ \"errorMessage\": \"" + message + "\" } ")
       		 .type("application/json")
       		 .build());
    }

}