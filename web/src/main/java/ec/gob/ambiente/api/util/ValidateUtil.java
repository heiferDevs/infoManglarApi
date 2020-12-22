package ec.gob.ambiente.api.util;

import ec.gob.ambiente.api.controller.UserInfoManglarController;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.services.RoleFacade;

public class ValidateUtil {

	public boolean passRole(RoleFacade roleFacade, Integer roleId){
    	Role role = (Role) roleFacade.find(roleId);
    	return passRole(role);
	}

	public boolean passRole(Role role){
    	if (role == null) return false;

    	boolean isSocio = role.getRoleName().equals(UserInfoManglarController.ROL_SOCIO_INFO_MANGLAR);
    	boolean isOrg = role.getRoleName().equals(UserInfoManglarController.ROL_ORG_INFO_MANGLAR);
    	boolean isMae = role.getRoleName().equals(UserInfoManglarController.ROL_MAE_INFO_MANGLAR);
    	boolean isInp = role.getRoleName().equals(UserInfoManglarController.ROL_INP_INFO_MANGLAR);
    	boolean isSuperAdmin = role.getRoleName().equals(UserInfoManglarController.ROL_SUPER_ADMIN_INFO_MANGLAR);
    	
    	return isSocio || isOrg || isMae || isInp || isSuperAdmin;
	}

}
