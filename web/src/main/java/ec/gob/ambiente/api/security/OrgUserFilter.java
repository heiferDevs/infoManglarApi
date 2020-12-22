package ec.gob.ambiente.api.security;

import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.controller.UserInfoManglarController;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;

import javax.ejb.EJB;
import javax.servlet.annotation.WebFilter;


@WebFilter(filterName = "ReporterUserFilter", urlPatterns = {"/rest/anomaly-form-reporter/*"})
public class OrgUserFilter extends BasicAuthenticationFilter {

	@EJB
	private UserFacade userFacade;

	@EJB
	private RolesUserFacade rolesUserFacade;

  public boolean isValidUser(String username, String password){
		return new AccessController(userFacade, null, rolesUserFacade, null)
		.accessUserWithRole(username, password, UserInfoManglarController.ROL_ORG_INFO_MANGLAR);
  }

}
