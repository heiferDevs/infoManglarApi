package ec.gob.ambiente.api.resource;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.controller.CedulaController;
import ec.gob.ambiente.api.controller.RegisterController;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.model.GeographicalLocation;
import ec.gob.ambiente.enlisy.model.Nationality;
import ec.gob.ambiente.enlisy.model.TreatmentsType;
import ec.gob.ambiente.enlisy.services.ContactFacade;
import ec.gob.ambiente.enlisy.services.ContactsFormFacade;
import ec.gob.ambiente.enlisy.services.GeographicalLocationFacade;
import ec.gob.ambiente.enlisy.services.NationalityFacade;
import ec.gob.ambiente.enlisy.services.TreatmentsTypeFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.exceptions.ServiceException;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.AllowedUserFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;
import ec.gob.registrocivil.consultacedula.Cedula;

@Path("/register")
public class RegisterResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private ContactFacade contactFacade;

	@EJB
	private ContactsFormFacade contactsFormFacade;

	@EJB
	private TreatmentsTypeFacade treatmentsTypeFacade;

	@EJB
	private NationalityFacade nationalityFacade;

	@EJB
	private GeographicalLocationFacade geographicalLocationFacade;

	@EJB
	private AllowedUserFacade allowedUserFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(UserRegister u) {
		RegisterController accessController = new RegisterController(
				userFacade, contactFacade, allowedUserFacade,
				contactsFormFacade, organizationsUserFacade,
				organizationManglarFacade);
		boolean success = accessController.save(u.getName(), u.getPin(),
				u.getGender(), u.getCivilStatus(), u.getTreatmentId(),
				u.getNationalityId(), u.getPhone(), u.getMobile(),
				u.getEmail(), u.getAddress(), u.getParroquiaId(),
				u.getOrganizationManglarId());
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE + " "
				+ accessController.getMsg());
	}

	@POST
	@Path("/recover-password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataRecoverResponse recoverPassword(DataRecover dataRecover) {
		RegisterController accessController = new RegisterController(
				userFacade, contactFacade, allowedUserFacade,
				contactsFormFacade, organizationsUserFacade,
				organizationManglarFacade);
		boolean success = accessController.recuperarClave(dataRecover
				.getUserPin());
		if (success) {
			String userEmail = accessController.getUser().getPeople()
					.getEmail();
			return new DataRecoverResponse(userEmail,
					DataResponse.SUCCESS_STATE);
		}
		return new DataRecoverResponse("", DataResponse.ERROR_STATE + " "
				+ accessController.getMsg());
	}

	@POST
	@Path("/change-password")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse changePassword(DataChangePassword dataChangePassword) {
		RegisterController accessController = new RegisterController(
				userFacade, contactFacade, allowedUserFacade,
				contactsFormFacade, organizationsUserFacade,
				organizationManglarFacade);
		boolean success = accessController.changePassword(
				dataChangePassword.getUserPin(),
				dataChangePassword.getPassword(),
				dataChangePassword.getNewPassword(),
				dataChangePassword.getNewPasswordConfirm());
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE + " "
				+ accessController.getMsg());
	}

	@GET
	@Path("/treatments")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> treatments() {
		List<Data> data = new ArrayList<>();
		try {
			List<TreatmentsType> list = treatmentsTypeFacade.findByStatus(true);
			for (TreatmentsType item : list) {
				Data d = new Data(item.getTrtyId(), item.getTrtyName());
				data.add(d);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return data;
	}

	@GET
	@Path("/nationalities")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> nationalities() {
		List<Data> data = new ArrayList<Data>();
		try {
			List<Nationality> list = nationalityFacade.findByStatus(true);
			for (Nationality item : list) {
				Data d = new Data(item.getNatiId(), item.getNatiDescription());
				data.add(d);
			}
		} catch (ServiceException e) {
			e.printStackTrace();
		}
		return data;
	}

	@GET
	@Path("/locations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> locations(
			@QueryParam("parentId") @DefaultValue("1") String parentId) {
		List<Data> data = new ArrayList<Data>();
		List<GeographicalLocation> list = geographicalLocationFacade
				.findByParentId(Integer.valueOf(parentId));
		for (GeographicalLocation item : list) {
			Data d = new Data(item.getGeloId(), item.getGeloName());
			data.add(d);
		}
		return data;
	}

	@GET
	@Path("/organizations")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> orgs() {
		List<Data> data = new ArrayList<Data>();
		List<OrganizationManglar> list = organizationManglarFacade.findAll();
		for (OrganizationManglar item : list) {
			Data d = new Data(item.getOrganizationManglarId(), item.getOrganizationManglarName());
			data.add(d);
		}
		return data;
	}

	@GET
	@Path("/organizations-by-type/{type}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Data> orgsByType(@PathParam("type") String type) {
		List<Data> data = new ArrayList<Data>();
		String typeToUse = "socio".equals(type) ? "org" : type;
		List<OrganizationManglar> list = organizationManglarFacade.findByType(typeToUse);
		for (OrganizationManglar item : list) {
			Data d = new Data(item.getOrganizationManglarId(), item.getOrganizationManglarName());
			data.add(d);
		}
		return data;
	}

	@GET
	@Path("/pin/{pin}")
	@Produces(MediaType.APPLICATION_JSON)
	public Cedula getCedula(@PathParam("pin") String pin) {
		return new CedulaController().getCedula(pin);
	}

}

class Data extends DataResponse {
	@Getter
	@Setter
	private Integer id;

	public Data(Integer id, String state) {
		super(state);
		this.id = id;
	}
}

class DataRemove {
	@Getter
	@Setter
	private Integer id;
}

class DataChangePassword {
	@Getter
	@Setter
	private String userPin, password, newPassword, newPasswordConfirm;
}

class DataRecover {
	@Getter
	@Setter
	private String userPin;
}

class DataRecoverResponse {
	@Getter
	@Setter
	private String userEmail, state;

	public DataRecoverResponse(String userEmail, String state) {
		super();
		this.userEmail = userEmail;
		this.state = state;
	}
}

class UserRegister {
	@Getter
	@Setter
	private String pin;
	@Getter
	@Setter
	private String name;
	@Getter
	@Setter
	private String treatmentId;
	@Getter
	@Setter
	private String nationalityId;
	@Getter
	@Setter
	private String phone;
	@Getter
	@Setter
	private String mobile;
	@Getter
	@Setter
	private String email;
	@Getter
	@Setter
	private String address;
	@Getter
	@Setter
	private String gender;
	@Getter
	@Setter
	private String civilStatus;
	@Getter
	@Setter
	private String parroquiaId;
	@Getter
	@Setter
	private Integer organizationManglarId;
}
