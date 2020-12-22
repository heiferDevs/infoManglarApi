package ec.gob.ambiente.api.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.infomanglar.forms.model.ConfigForm;
import ec.gob.ambiente.infomanglar.forms.services.ConfigFormFacade;

@Path("/")
public class ConfigFormResource {

	@EJB
	private ConfigFormFacade configFormFacade;

	@POST
	@Path("/config-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ConfigForm configForm) {
		boolean success = configFormFacade.save(configForm);
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}


	@GET
	@Path("/config-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public ConfigForm getLast() {
		ConfigForm form = configFormFacade.getLast();
		if (form != null) return form;
		return new ConfigForm();
	}

}
