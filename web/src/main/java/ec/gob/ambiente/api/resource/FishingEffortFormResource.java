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
import ec.gob.ambiente.infomanglar.forms.model.FishingEffortForm;
import ec.gob.ambiente.infomanglar.forms.services.FishingEffortFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class FishingEffortFormResource {

	@EJB
	private FishingEffortFormFacade fishingEffortFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/fishing-effort-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(FishingEffortForm fishingEffortForm) {
		String changeType = fishingEffortForm.getFishingEffortFormId() == null ? "created" : "edited";
		boolean success = fishingEffortFormFacade.save(fishingEffortForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, fishingEffortForm.getUserId(),
					fishingEffortForm.getFishingEffortFormId(), fishingEffortForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/fishing-effort-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<FishingEffortForm> get() {
		return fishingEffortFormFacade.findAll();
	}

	@GET
	@Path("/fishing-effort-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FishingEffortForm getById(@PathParam("form-id") Integer formId) {
		return fishingEffortFormFacade.find(formId);
	}

	@GET
	@Path("/fishing-effort-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public FishingEffortForm getLast(@PathParam("org-id") Integer orgId) {
		FishingEffortForm form = fishingEffortFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new FishingEffortForm();
	}

}
