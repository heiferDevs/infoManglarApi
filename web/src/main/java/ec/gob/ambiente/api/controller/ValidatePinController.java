package ec.gob.ambiente.api.controller;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.util.ValidateUtil;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;

public class ValidatePinController {
	
	@Getter
    @Setter
    private String msg;

	@Getter
    @Setter
    private String userName;

	@Getter
    @Setter
	private Role role;

	@Getter
    @Setter
	private OrganizationManglar organizationManglar;

	@Getter
    @Setter
	private GeographicalLocation geographicalLocation;

    private UserFacade userFacade;
    private RoleFacade roleFacade;
	private AllowedUserFacade allowedUserFacade;

	public ValidatePinController(UserFacade userFacade,
			AllowedUserFacade allowedUserFacade,
			RoleFacade roleFacade) {
		super();
		this.userFacade = userFacade;
		this.allowedUserFacade = allowedUserFacade;
		this.roleFacade = roleFacade;
	}

	public boolean validate(String userPin) {

		if (userPin == null || userPin.isEmpty() || userPin.length() != 10){
			msg = "Debe ingresar una cédula valida";
			return false;			
		}

		AllowedUser allowedUser = allowedUserFacade.findByUserPin(userPin);
		boolean userIsNotMember = allowedUser == null;
		if ( userIsNotMember ) {
			msg = "El usuario no es socio de organización";
			return false;
		}

		boolean passRole = new ValidateUtil().passRole(roleFacade, allowedUser.getRole().getRoleId());
    	if( !passRole ) {
			msg = "El usuario no tiene asignados los perfiles para este sistema. Por favor comuníquese con Mesa de Ayuda.";
			return false;
		}

    	// Validate if user exists
		boolean userExists = userFacade.findByUserName(userPin).getUserId() != null;
		if ( userExists ) {
			msg = "El usuario ya se encuentra registrado";
			return false;
		}

		// SUCCESS
		userName = allowedUser.getAllowedUserName();
		organizationManglar = allowedUser.getOrganizationManglar();
		geographicalLocation = allowedUser.getGeographicalLocation();
		return true;
	}

}
