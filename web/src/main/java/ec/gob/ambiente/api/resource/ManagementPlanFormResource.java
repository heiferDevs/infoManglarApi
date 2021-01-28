package ec.gob.ambiente.api.resource;

import java.util.ArrayList;
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
import ec.gob.ambiente.api.mapping.MappingUtil;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.api.reports.ReportsUtil;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.services.ManagementPlanFormFacade;
import ec.gob.ambiente.infomanglar.model.PlanInfo;
import ec.gob.ambiente.infomanglar.model.PlannedActivity;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/management-plan-form")
public class ManagementPlanFormResource {

	@EJB
	private ManagementPlanFormFacade managementPlanFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ManagementPlanForm managementPlanForm) {
		// update Data before save
		for( PlanInfo planInfo : managementPlanForm.getPlansInfo()) {
			planInfo.setManagementPlanForm(managementPlanForm);
			recursiveUpdatePlansInfo(managementPlanForm.getPlansInfo());
		}
		String changeType = managementPlanForm.getManagementPlanFormId() == null ? "created" : "edited";
		boolean success = managementPlanFormFacade.save(managementPlanForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, managementPlanForm.getUserId(),
					managementPlanForm.getManagementPlanFormId(), managementPlanForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	private void recursiveUpdatePlansInfo(List<PlanInfo> plansInfo){
		for (PlanInfo planInfo : plansInfo) {
			for (PlanInfo child : planInfo.getPlansInfo()) {
				child.setPlanInfoParent(planInfo);
			}
			recursiveUpdatePlansInfo(planInfo.getPlansInfo());
		}
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ManagementPlanForm> get() {
		return managementPlanFormFacade.findAll();
	}


	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ManagementPlanForm getById(@PathParam("form-id") Integer formId) {
		return managementPlanFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ManagementPlanForm getLast(@PathParam("org-id") Integer orgId) {
		ManagementPlanForm form = managementPlanFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new ManagementPlanForm();
	}

	@GET
	@Path("/activities/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<String> getSectors(@PathParam("org-id") Integer orgId) {
		ManagementPlanForm form = managementPlanFormFacade.getLastByOrg(orgId);
		return ReportsUtil.getFromManagmentPlanForm(form, "activity");
	} // TODO: Move this endpoint to other class

}
