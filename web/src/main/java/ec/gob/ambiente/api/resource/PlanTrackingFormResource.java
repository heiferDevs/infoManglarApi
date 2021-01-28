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
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.services.PlanTrackingFormFacade;
import ec.gob.ambiente.infomanglar.model.PlannedActivity;
import ec.gob.ambiente.infomanglar.services.EmailNotificationFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;

@Path("/plan-tracking-form")
public class PlanTrackingFormResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private PlanTrackingFormFacade planTrackingFormFacade;

	@EJB
	private EmailNotificationFacade emailNotificationFacade;

	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(PlanTrackingForm planTrackingForm) {
		// update Data before save
		for( PlannedActivity plannedActivity : planTrackingForm.getPlannedActivities()) {
			plannedActivity.setPlanTrackingForm(planTrackingForm);
		}
		String changeType = planTrackingForm.getPlanTrackingFormId() == null ? "created" : "edited";
		boolean success = planTrackingFormFacade.save(planTrackingForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, planTrackingForm.getUserId(),
					planTrackingForm.getPlanTrackingFormId(), planTrackingForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PlanTrackingForm> get() {
		return planTrackingFormFacade.findAll();
	}


	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlanTrackingForm getById(@PathParam("form-id") Integer formId) {
		return planTrackingFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PlanTrackingForm getLast(@PathParam("org-id") Integer orgId) {
		PlanTrackingForm form = planTrackingFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new PlanTrackingForm();
	}

}


