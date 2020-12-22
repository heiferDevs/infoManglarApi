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
import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.services.ControlFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class ControlFormResource {

	@EJB
	private ControlFormFacade controlFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/control-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ControlForm controlForm) {
		// update Data before save
		for( FileForm fileForm : controlForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setControlForm(controlForm);
		}
		String changeType = controlForm.getControlFormId() == null ? "created" : "edited";
		boolean success = controlFormFacade.save(controlForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, controlForm.getUserId(),
					controlForm.getControlFormId(), controlForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/control-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ControlForm> get() {
		return controlFormFacade.findAll();
	}

	@GET
	@Path("/control-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ControlForm getLast(@PathParam("org-id") Integer orgId) {
		ControlForm form = controlFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new ControlForm();
	}

	@GET
	@Path("/control-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ControlForm getById(@PathParam("form-id") Integer formId) {
		return controlFormFacade.find(formId);
	}

}


