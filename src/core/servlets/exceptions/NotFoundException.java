package core.servlets.exceptions;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

public class NotFoundException extends WebApplicationException {

     /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NotFoundException() {
         super(Response.status(Status.NOT_FOUND)
        		 .build());
     }

     public NotFoundException(String message) {
         super(Response.status(Status.NOT_FOUND)
        		 .entity("{ \"errorMessage\": \"" + message + "\" } ")
        		 .type("application/json")
        		 .build());
     }

}