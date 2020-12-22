package ec.gob.ambiente.api.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/organization-manglar")
public class OrganizationManglarResource {

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(OrganizationManglar organizationManglar) {
		boolean success = organizationManglarFacade.save(organizationManglar);
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrganizationManglar> get() {
		return organizationManglarFacade.findAll();
	}

	@GET
	@Path("/get-by-id/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrganizationManglar getById(@PathParam("org-id") Integer organizationManglarId) {
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(organizationManglarId);
		if (organizationManglar == null){
			return new OrganizationManglar();
		}
		return organizationManglar;
	}

	@POST
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponse remove(DataRemove dataRemove) {
		OrganizationManglar organizationManglar = organizationManglarFacade.find(dataRemove.getId());
		organizationManglarFacade.remove(organizationManglar);
		return new DataResponse(DataResponse.SUCCESS_STATE);
	}
	
}

