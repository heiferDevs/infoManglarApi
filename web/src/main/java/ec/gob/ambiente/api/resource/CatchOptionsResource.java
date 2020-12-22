package ec.gob.ambiente.api.resource;

import javax.ws.rs.OPTIONS;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("{path : .*}")
public class CatchOptionsResource {

	@OPTIONS
	public Response options() {
	    return Response.ok("")
	            .build();
	}

}

