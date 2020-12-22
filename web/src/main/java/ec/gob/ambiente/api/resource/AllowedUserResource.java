package ec.gob.ambiente.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.controller.AllowedUserController;
import ec.gob.ambiente.api.controller.UserInfoManglarController;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.services.GeographicalLocationFacade;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/allowed-user")
public class AllowedUserResource {

	@EJB
	private AllowedUserFacade allowedUserFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private GeographicalLocationFacade geographicalLocationFacade;

	@EJB
	private RoleFacade roleFacade;

	@POST
	@Path("/save/{user-type}")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(@PathParam("user-type") String userType, TransferAllowedUser transferAllowedUser) {
		AllowedUserController allowedUserController = new AllowedUserController(allowedUserFacade, roleFacade, organizationManglarFacade, geographicalLocationFacade);
		String roleName = AccessController.getRoleName(userType);
		if (roleName == null) {
			return new DataResponse("ERROR No se pudo guardar el usuario de organización");			
		}
		boolean success = allowedUserController.save(
				transferAllowedUser.getAllowedUser(),
				roleName,
				transferAllowedUser.getOrganizationManglarId(),
				transferAllowedUser.getGeloId());
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse("ERROR " + allowedUserController.getMsg());
	}

	@GET
	@Path("/get/{user-type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InfoAllowedUser> get(@PathParam("user-type") String userType) {
		List<InfoAllowedUser> result = new ArrayList<>();
		String roleName = AccessController.getRoleName(userType);
		if (roleName == null) {
			return result;			
		}
		List<AllowedUser> allowedUsers = allowedUserFacade
			.findByRoleName(roleName);
		for ( AllowedUser allowedUser : allowedUsers ) {
			result.add(new InfoAllowedUser(allowedUser));
		}
		return result;
	}

	@GET
	@Path("/get-by-id/{user-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InfoAllowedUser getById(@PathParam("user-id") Integer allowedUserId) {
		AllowedUser allowedUser = allowedUserFacade.findById(allowedUserId);
		if (allowedUser == null){
			return new InfoAllowedUser();
		}
		return new InfoAllowedUser(allowedUser);
	}

	@POST
	@Path("/remove")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponse remove(DataRemove dataRemove) {
		AllowedUser allowedUser = allowedUserFacade.find(dataRemove.getId());
		allowedUserFacade.remove(allowedUser);
		return new DataResponse(DataResponse.SUCCESS_STATE);
	}

}

class TransferAllowedUser {
	@Getter
	@Setter
	private Integer organizationManglarId;
	@Getter
	@Setter
	private Integer geloId;
	@Getter
	@Setter
	private AllowedUser allowedUser;
}

class InfoAllowedUser {
	@Getter
	@Setter
	private Integer allowedUserId;
	@Getter
	@Setter
	private Boolean allowedUserStatus;
	@Getter
	@Setter
	private String organizationManglarName;
	@Getter
	@Setter
	private String geographicalLocationName;
	@Getter
	@Setter
	private String allowedUserName;
	@Getter
	@Setter
	private String allowedUserPin;

	public InfoAllowedUser() {
		super();
	}

	public InfoAllowedUser(AllowedUser allowedUser) {
		super();
		this.allowedUserId = allowedUser.getAllowedUserId();
		this.allowedUserStatus = allowedUser.getAllowedUserStatus();
		this.organizationManglarName = allowedUser.getOrganizationManglar().getOrganizationManglarName();
		this.geographicalLocationName = allowedUser.getGeographicalLocation().getGeloName();
		this.allowedUserName = allowedUser.getAllowedUserName();
		this.allowedUserPin = allowedUser.getAllowedUserPin();
	}
}
