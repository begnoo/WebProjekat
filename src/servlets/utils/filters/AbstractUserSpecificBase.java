package servlets.utils.filters;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.UUID;

import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.core.UriInfo;

import org.apache.commons.io.Charsets;
import org.apache.commons.io.IOUtils;

import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings({ "deprecation", "unchecked" })
public abstract class AbstractUserSpecificBase extends AbstractFilterBase {
	protected UUID getEntityId(ContainerRequestContext requestContext, String entityIdName) {
    	UriInfo uriInfo = requestContext.getUriInfo();
    	
    	if(uriInfo.getPathParameters().containsKey(entityIdName)) {
    		return UUID.fromString(uriInfo.getPathParameters().getFirst(entityIdName));
    	}
    	
    	if(uriInfo.getQueryParameters().containsKey(entityIdName)) {
    		return UUID.fromString(uriInfo.getQueryParameters().getFirst(entityIdName));
    	}

    	try {
            String json = IOUtils.toString(requestContext.getEntityStream(), Charsets.UTF_8);
            if(!json.isBlank())
            {
                ObjectMapper mapper = new ObjectMapper();
    			HashMap<String, String> bodyParamethers = mapper.readValue(json, HashMap.class);
                InputStream in = IOUtils.toInputStream(json);
                requestContext.setEntityStream(in);

                if(bodyParamethers.containsKey(entityIdName)) {
                	return UUID.fromString(bodyParamethers.get(entityIdName));
                }            
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    
    	return null;
    }
}
