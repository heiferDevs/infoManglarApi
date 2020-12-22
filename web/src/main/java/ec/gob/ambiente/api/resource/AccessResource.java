package ec.gob.ambiente.api.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;

@Path("/login")
public class AccessResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private RoleFacade roleFacade;
	
	@EJB
	private RolesUserFacade rolesUserFacade;

	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@POST
	@Path("/access")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataUserAccess accessSocio(UserAccess userAccess) {
		AccessController accessController = new AccessController(userFacade, roleFacade, rolesUserFacade, organizationsUserFacade);
		boolean success = accessController.accessUserAnyInfoManglarRole(userAccess.getUsername(), userAccess.getPassword());
		if (success) {
			User user = accessController.getUser();
			String completeUserName = user.getPeople().getPeopName();
			String typeUser = accessController.getTypeUser();
			OrganizationManglar organizationManglar = accessController.getOrganizationManglar();
			return new DataUserAccess(DataResponse.SUCCESS_STATE, user.getUserId(), organizationManglar.getOrganizationManglarId(), completeUserName, user.getPeople().getEmail(), user.getUserPin(), typeUser);
		}
		return new DataUserAccess(DataResponse.ERROR_STATE + " " + accessController.getMsg(), null, null, null, null, null, null);
	}
	
}

class DataUserAccess {
	@Getter
    @Setter
	private String state;
	@Getter
    @Setter
    private Integer userId;
	@Getter
    @Setter
    private Integer organizationManglarId;
	@Getter
    @Setter
    private String userName;
	@Getter
    @Setter
    private String userEmail;
	@Getter
    @Setter
    private String userPin;
	@Getter
    @Setter
    private String typeUser;
	public DataUserAccess(String state, Integer userId, Integer organizationManglarId,
			String userName, String userEmail, String userPin, String typeUser) {
		super();
		this.state = state;
		this.userId = userId;
		this.organizationManglarId = organizationManglarId;
		this.userName = userName;
		this.userEmail = userEmail;
		this.userPin = userPin;
		this.typeUser = typeUser;
	}


}

class UserAccess {
	@Getter
    @Setter
	private String username, password;
}
