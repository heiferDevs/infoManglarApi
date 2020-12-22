package ec.gob.ambiente.api.mapping;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.json.simple.JSONObject;

import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.InfoVedaFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.MappingFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/mapping")
public class MappingResource {

	@EJB
	private MappingFormFacade mappingFormFacade;

	@EJB
	private LimitsFormFacade limitsFormFacade;

	@EJB
	private UserFacade userFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private RolesUserFacade rolesUserFacade;

	@EJB
	private ShellSizeFormFacade shellSizeFormFacade;
	
	@EJB
	private ShellCollectionFormFacade shellCollectionFormFacade;
	
	@EJB
	private CrabSizeFormFacade crabSizeFormFacade;
	
	@EJB
	private CrabCollectionFormFacade crabCollectionFormFacade;
	
	@EJB
	private InfoVedaFormFacade infoVedaFormFacade;
	
	@GET
	@Path("/sectors/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getSectors(@PathParam("org-id") Integer orgId) {
		return new MappingUtil(mappingFormFacade, limitsFormFacade).getSectors(orgId);
	}

	@GET
	@Path("/filter/{form-type}/{user-type}/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public JSONObject filter(
			@PathParam("form-type") String formType,
			@PathParam("user-type") String userType,
			@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs
			) {
		return new FilterMappingUtil(userFacade, organizationManglarFacade, rolesUserFacade,
				shellSizeFormFacade, shellCollectionFormFacade, crabSizeFormFacade, crabCollectionFormFacade, infoVedaFormFacade, mappingFormFacade, limitsFormFacade)
			.get(formType, userType, userId, orgId, startTs, endTs);
	}

	@GET
	@Path("/filter-file/{form-type}/{user-type}/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response filterFile(
			@PathParam("form-type") String formType,
			@PathParam("user-type") String userType,
			@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs
			) {
		JSONObject jsonObject = new FilterMappingUtil(userFacade, organizationManglarFacade,rolesUserFacade,
				shellSizeFormFacade, shellCollectionFormFacade, crabSizeFormFacade, crabCollectionFormFacade, infoVedaFormFacade, mappingFormFacade, limitsFormFacade)
			.get(formType, userType, userId, orgId, startTs, endTs);
		Response.ResponseBuilder response = Response.ok();
		response.header("Content-Disposition", "attachment; filename=\"result.geojson\"");
		response.entity(jsonObject.toString());
		return response.build();
	}

}
