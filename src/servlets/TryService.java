package servlets;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.ServletContext;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import model.TestObject;

@Path("/try")
public class TryService {

	@Context
	ServletContext servletContext;
	
	
	@GET
	@Path("/")
	@Produces(MediaType.APPLICATION_JSON)
	public Collection<TestObject> getTestObjects(){
		Collection<TestObject> testObjects = new ArrayList<>();
		
		testObjects.add(new TestObject(22));
		testObjects.add(new TestObject(11));
		testObjects.add(new TestObject(33));

		return testObjects;
	}
}
