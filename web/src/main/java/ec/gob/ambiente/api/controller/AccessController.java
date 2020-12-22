package ec.gob.ambiente.api.controller;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.util.ValidateUtil;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.model.RolesUser;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.enums.TipoUsuarioEnum;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;
import ec.gob.ambiente.suia.utils.JsfUtil;

public class AccessController {
	
	@Getter
	@Setter
	private String msg;
	
	@Getter
	@Setter
	private User user;
	
	@Getter
	@Setter
	private String typeUser;

	@Getter
	@Setter
	private OrganizationManglar organizationManglar;

	private UserFacade userFacade;
	private RoleFacade roleFacade;
	private RolesUserFacade rolesUserFacade;
	private OrganizationsUserFacade organizationsUserFacade;

	public AccessController(UserFacade userFacade, RoleFacade roleFacade,
			RolesUserFacade rolesUserFacade,
			OrganizationsUserFacade organizationsUserFacade) {
		super();
		this.userFacade = userFacade;
		this.roleFacade = roleFacade;
		this.rolesUserFacade = rolesUserFacade;
		this.organizationsUserFacade = organizationsUserFacade;
	}

	public boolean accessUserAnyInfoManglarRole(String username, String password) {
		try {
			boolean successValidate = verificarUsuario(username, password);
			if (!successValidate) {
				return false;
			}
			// Validate role
			List<RolesUser> rolesUsers = rolesUserFacade.findByUserName(username);
			Role roleInfoManglar = getRoleInfoManglar(rolesUsers);
			if( roleInfoManglar == null ) {
				msg = "El usuario no tiene asignados los perfiles para este sistema. Por favor comuníquese con Mesa de Ayuda.";
				return false;
			}
			organizationManglar = organizationsUserFacade.findFirstByUserId(user.getUserId()); 
			if( organizationManglar == null ) {
				msg = "La organización a la que pertenece no es valida";
				return false;
			}
			typeUser = getTypeUser(roleInfoManglar.getRoleName());
			return true;
		} catch (IllegalStateException e) {
			msg = "El usuario no tiene asignados los perfiles para este sistema. Por favor comuníquese con Mesa de Ayuda.";	
		}
		return false;
	}

	// Used for BasicAuth MANTEIN SIMPLE AS POSIBLE
	public boolean accessUserWithRole(String username, String password, String roleName) {
		try {
			boolean successValidate = verificarUsuario(username, password);
			if (!successValidate) {
				return false;
			}
			// Validate role
			List<RolesUser> rolesUsers = rolesUserFacade.findByUserNameAndRoleName(username, roleName);
			if( rolesUsers == null ) {
				msg = "El usuario no tiene asignados los perfiles para este sistema. Por favor comuníquese con Mesa de Ayuda.";
				return false;
			}
			return true;
		} catch (IllegalStateException e) {
			msg = "El usuario no tiene asignados los perfiles para este sistema. Por favor comuníquese con Mesa de Ayuda.";	
		}
		return false;
	}

	public boolean verificarUsuario(String username, String password) {
		
		user = userFacade.findByUserName(username);
		
		if(user.getUserId() == null) {
			msg = "Usuario no encontrado: " + username;
			user=userFacade.findByUserNameDisabled(username);
			if(user.getUserId() != null && (user.getUserStatus()==null || !user.getUserStatus())) {
				msg = "El usuario (" + username + ") se encuentra desactivado. Por favor comuniquese con Mesa de Ayuda (maetransparente@ambiente.gob.ec)";			
			}
			return false;
		}
		// si el usuario está desactivado
		if(user.getUserStatus()==null || !user.getUserStatus()) {
			msg = "El usuario (" + username + ") se encuentra desactivado. Por favor comuniquese con Mesa de Ayuda (maetransparente@ambiente.gob.ec)";
			return false;
		}
		
		try {// If para verificar si la clave ingresada coincide con la clave de
			// bdd
			if (!user.getUserPassword().equals(JsfUtil.claveEncriptadaSHA1(password))
				&& !(user.getUserPassword().equals(password) && password.length() == 40)) {
				msg = "Clave de Usuario (" + username + ") incorrecta.";
				return false;				
			}
		} catch (Exception e) {
			e.printStackTrace();
			msg = "Error Password: " + e.getMessage();
			return false;				
		}
		return true;
	}

	private Role getRoleInfoManglar(List<RolesUser> rolesUsers){
		if( rolesUsers == null ) {
			return null;
		}
		for ( RolesUser rolesUser : rolesUsers ) {
			Role role = rolesUser.getRole();
			if ( new ValidateUtil().passRole(role) ) {
				return role;
			}
		}
		return null;
	}

	public static String getTypeUser(String roleName){
        switch(roleName){
                case UserInfoManglarController.ROL_SOCIO_INFO_MANGLAR:
                        return "socio";
                case UserInfoManglarController.ROL_ORG_INFO_MANGLAR:
                        return "org";
                case UserInfoManglarController.ROL_MAE_INFO_MANGLAR:
                        return "mae";
                case UserInfoManglarController.ROL_INP_INFO_MANGLAR:
                        return "inp";
                case UserInfoManglarController.ROL_SUPER_ADMIN_INFO_MANGLAR:
                        return "super-admin";
        }
        return null;
	}

	public static String getRoleName(String userType){
        switch(userType){
                case "socio":
                        return UserInfoManglarController.ROL_SOCIO_INFO_MANGLAR;
                case "org":
                        return UserInfoManglarController.ROL_ORG_INFO_MANGLAR;
                case "mae":
                        return UserInfoManglarController.ROL_MAE_INFO_MANGLAR;
                case "inp":
                        return UserInfoManglarController.ROL_INP_INFO_MANGLAR;
                case "super-admin":
                        return UserInfoManglarController.ROL_SUPER_ADMIN_INFO_MANGLAR;
        }
        return null;
	}

}
