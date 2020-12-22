package ec.gob.ambiente.api.security;

import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.controller.UserInfoManglarController;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;

import javax.ejb.EJB;
import javax.servlet.annotation.WebFilter;

@WebFilter(filterName = "AdminUserFilter", urlPatterns = {"/rest/anomaly-form/*", "/rest/email-notification/*", "/rest/organization-manglar-ssdsds/*"})
public class SuperAdminUserFilter extends BasicAuthenticationFilter {

	@EJB
	private UserFacade userFacade;
	
	@EJB
	private RolesUserFacade rolesUserFacade;

  public boolean isValidUser(String username, String password){
		return new AccessController(userFacade, null, rolesUserFacade, null)
			.accessUserWithRole(username, password, UserInfoManglarController.ROL_SUPER_ADMIN_INFO_MANGLAR);
  }

}
