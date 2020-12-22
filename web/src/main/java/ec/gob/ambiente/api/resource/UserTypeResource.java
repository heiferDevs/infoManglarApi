package ec.gob.ambiente.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;

@Path("/user-type")
public class UserTypeResource {

	@EJB
	private RolesUserFacade rolesUserFacade;

	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@GET
	@Path("/{user-type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DataUserType> get(@PathParam("user-type") String userType) {
		String roleName = AccessController.getRoleName(userType);
		if (roleName == null) {
			return new ArrayList<DataUserType>();
		}
		List<User> users = rolesUserFacade.findByRoleName(roleName);
		if ( users == null ) {
			return new ArrayList<DataUserType>();
		}
		List<DataUserType> usersType = new ArrayList<>();
		for ( User user : users ) {
			usersType.add(new DataUserType(user, userType));
		}
		return usersType;
	}

	@GET
	@Path("/by-org/{user-type}/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DataUserType> get(@PathParam("user-type") String userType, @PathParam("org-id") Integer orgId) {
		String roleName = AccessController.getRoleName(userType);
		if (roleName == null) {
			return new ArrayList<DataUserType>();
		}
		List<User> usersByOrgAndRole = organizationsUserFacade.findByOrgIdAndRole(orgId, roleName);
		if ( usersByOrgAndRole == null) {
			return new ArrayList<DataUserType>();
		}
		List<DataUserType> usersType = new ArrayList<>();
		for ( User user : usersByOrgAndRole ) {
			usersType.add(new DataUserType(user, userType));
		}
		return usersType;
	}

}

class DataUserType {
	@Getter
    @Setter
    private Integer userId;
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
    private String userType;

	public DataUserType(User user, String userType) {
		this.userId = user.getUserId();
		this.userName = user.getPeople().getPeopName();
		this.userEmail = user.getPeople().getEmail();
		this.userPin = user.getUserName();
		this.userType = userType;
	}

}

