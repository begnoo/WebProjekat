package core.servlets.exceptions;

import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BadRequestException extends WebApplicationException {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public BadRequestException() {
        super(Response.status(Status.BAD_REQUEST)
       		 .build());
    }

    public BadRequestException(String message) {
        super(Response.status(Status.BAD_REQUEST)
       		 .entity("{ \"errorMessage\": \"" + message + "\" } ")
       		 .type("application/json")
       		 .build());
    }
    
    public BadRequestException(List<String> errorMessages) {
        super(Response.status(Status.BAD_REQUEST)
          		 .entity("{ \"errorMessages\": " + generateJsonArray(errorMessages) + " } ")
       		 .type("application/json")
       		 .build());
    }
    
	protected static String generateJsonArray(List<String> messages)
	{
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			return mapper.writeValueAsString(messages);
			} catch (JsonProcessingException e) {
			return "[]";
		}
	}
}