package ec.gob.ambiente.api.resource;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.controller.ValidatePinController;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.services.GeographicalLocationFacade;
import ec.gob.ambiente.enlisy.services.RoleFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/")
public class ValidatePinResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private AllowedUserFacade allowedUserFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private GeographicalLocationFacade geographicalLocationFacade;

	@EJB
	private RoleFacade roleFacade;

	@POST
	@Path("/validate-pin")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataUserValidate validatePinSocio(DataValidate dataValidate) {
		ValidatePinController validatePinController = new ValidatePinController(userFacade, allowedUserFacade, roleFacade);
		boolean success = validatePinController.validate(dataValidate.getUserPin());
		if (success) {
			return new DataUserValidate(dataValidate.getUserPin(), validatePinController);
		}
		return new DataUserValidate(DataResponse.ERROR_STATE + " " + validatePinController.getMsg(), dataValidate.getUserPin());
	}

}

class DataValidate {
	@Getter
    @Setter
    private String userPin;
}

class DataUserValidate {
	@Getter
    @Setter
	private String state;
	@Getter
    @Setter
	private String pin;
	@Getter
    @Setter
	private String name;
	@Getter
    @Setter
	private String gender;
	@Getter
    @Setter
	private String civilStatus;
	@Getter
    @Setter
	private Integer organizationManglarId;
	@Getter
    @Setter
	private String organizationManglarName;
	@Getter
    @Setter
	private Integer provinceId;
	@Getter
    @Setter
	private String provinceName;
	
	public DataUserValidate(String state, String pin) {
		super();
		this.state = state;
		this.pin = pin;
	}

	public DataUserValidate(String userPin, ValidatePinController validatePinController) {
		super();
		OrganizationManglar organizationManglar = validatePinController.getOrganizationManglar();
		GeographicalLocation geographicalLocation = validatePinController.getGeographicalLocation();
		this.pin = userPin;
		this.name = validatePinController.getUserName();
		this.gender = "HOMBRE";
		this.civilStatus = "SOLTERO";
		this.organizationManglarId = organizationManglar.getOrganizationManglarId();
		this.organizationManglarName = organizationManglar.getOrganizationManglarName();
		this.provinceId = geographicalLocation.getGeloId();
		this.provinceName = geographicalLocation.getGeloName();
		this.state = DataResponse.SUCCESS_STATE;
	}

}

