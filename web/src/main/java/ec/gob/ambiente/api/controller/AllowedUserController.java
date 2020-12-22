package ec.gob.ambiente.api.controller;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.services.GeographicalLocationFacade;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.infomanglar.model.AllowedUser;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

public class AllowedUserController {
	
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

    private RoleFacade roleFacade;
	private AllowedUserFacade allowedUserFacade;
	private OrganizationManglarFacade organizationManglarFacade;
	private GeographicalLocationFacade geographicalLocationFacade;

	public AllowedUserController(AllowedUserFacade allowedUserFacade,
			RoleFacade roleFacade,
			OrganizationManglarFacade organizationManglarFacade,
			GeographicalLocationFacade geographicalLocationFacade) {
		super();
		this.allowedUserFacade = allowedUserFacade;
		this.roleFacade = roleFacade;
		this.organizationManglarFacade = organizationManglarFacade;
		this.geographicalLocationFacade = geographicalLocationFacade;
	}

	public boolean save(AllowedUser allowedUser, String roleName, Integer organizationManglarId, Integer geloId){
		// Set OrganizationManglar since json decode not created based on relation
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(organizationManglarId);
		if (organizationManglar == null){
			msg = "No existe la organizacion manglar a asignar";
			return false;
		}
		allowedUser.setOrganizationManglar(organizationManglar);

		// Set GeographicalLocation since json decode not created based on relation
		GeographicalLocation geographicalLocation = geographicalLocationFacade.findById(geloId);
		allowedUser.setGeographicalLocation(geographicalLocation);

		// Set GeographicalLocation since json decode not created based on relation
		Role role = (Role) roleFacade.findByName(roleName);
		if (role == null){
			msg = "No existe el rol a asignar";
			return false;
		}
		allowedUser.setRole(role);
		
		return allowedUserFacade.save(allowedUser);
	}

}
