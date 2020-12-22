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
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.services.EvidenceFormFacade;
import ec.gob.ambiente.infomanglar.model.EvidenceActivity;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class EvidenceFormResource {

	@EJB
	private EvidenceFormFacade evidenceFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/evidence-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(EvidenceForm evidenceForm) {
		// update Data before save
		for( EvidenceActivity evidenceActivity : evidenceForm.getEvidenceActivities()) {
			evidenceActivity.setEvidenceForm(evidenceForm);
			for( FileForm fileForm : evidenceActivity.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setEvidenceActivity(evidenceActivity);
			}
		}
		String changeType = evidenceForm.getEvidenceFormId() == null ? "created" : "edited";
		boolean success = evidenceFormFacade.save(evidenceForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, evidenceForm.getUserId(),
					evidenceForm.getEvidenceFormId(), evidenceForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/evidence-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EvidenceForm> get() {
		return evidenceFormFacade.findAll();
	}

	@GET
	@Path("/evidence-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EvidenceForm getById(@PathParam("form-id") Integer formId) {
		return evidenceFormFacade.find(formId);
	}

	@GET
	@Path("/evidence-form/get-last/{org-id}/{user-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EvidenceForm getLast(@PathParam("org-id") Integer orgId, @PathParam("user-id") Integer userId) {
		EvidenceForm form = evidenceFormFacade.getLastByOrgAndUser(orgId, userId);
		if (form != null) return form;
		return new EvidenceForm();
	}

}
