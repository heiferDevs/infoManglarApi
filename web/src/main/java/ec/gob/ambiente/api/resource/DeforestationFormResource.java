package ec.gob.ambiente.api.resource;

import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.history.HistoryChangeUtil;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.DeforestationForm;
import ec.gob.ambiente.infomanglar.forms.services.DeforestationFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class DeforestationFormResource {

	@EJB
	private DeforestationFormFacade deforestationFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/deforestation-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(DeforestationForm deforestationForm) {
		String changeType = deforestationForm.getDeforestationFormId() == null ? "created" : "edited";
		boolean success = deforestationFormFacade.save(deforestationForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, deforestationForm.getUserId(),
					deforestationForm.getDeforestationFormId(), deforestationForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/deforestation-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DeforestationForm> get() {
		return deforestationFormFacade.findAll();
	}

	@GET
	@Path("/deforestation-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeforestationForm getById(@PathParam("form-id") Integer formId) {
		return deforestationFormFacade.find(formId);
	}

	@GET
	@Path("/deforestation-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DeforestationForm getLast(@PathParam("org-id") Integer orgId) {
		DeforestationForm form = deforestationFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new DeforestationForm();
	}

}


