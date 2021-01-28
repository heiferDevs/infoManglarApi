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
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.model.OrgMapping;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/limits-form")
public class LimitsFormResource {

	@EJB
	private LimitsFormFacade limitsFormFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(LimitsForm limitsForm) {
		// update Data before save
		for( OrgMapping orgLimits : limitsForm.getOrgsMapping() ) {
			Integer organizationId = orgLimits.getOrganizationId();
			if (organizationId == null){
				OrganizationManglar organizationManglar = organizationManglarFacade.findByName(orgLimits.getOrganizationName());
				if (organizationManglar != null) organizationId = organizationManglar.getOrganizationManglarId();
			}
			orgLimits.setOrganizationId(organizationId);
			orgLimits.setLimitsForm(limitsForm);
			for( FileForm fileForm : orgLimits.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setOrgMapping(orgLimits);
			}
		}
		String changeType = limitsForm.getLimitsFormId() == null ? "created" : "edited";
		boolean success = limitsFormFacade.save(limitsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, limitsForm.getUserId(),
					limitsForm.getLimitsFormId(), limitsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<LimitsForm> get() {
		return limitsFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LimitsForm getById(@PathParam("form-id") Integer formId) {
		return limitsFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public LimitsForm getLast(@PathParam("org-id") Integer orgId) {
		LimitsForm form = limitsFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new LimitsForm();
	}

}
