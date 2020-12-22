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
import ec.gob.ambiente.infomanglar.forms.model.OrganizationInfoForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.services.OrganizationInfoFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class OrganizationInfoFormResource {

	@EJB
	private OrganizationInfoFormFacade organizationInfoFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/organization-info-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(OrganizationInfoForm organizationInfoForm) {
		// update Data before save
		for( FileForm fileForm : organizationInfoForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setOrganizationInfoForm(organizationInfoForm);
		}
		String changeType = organizationInfoForm.getOrganizationInfoFormId() == null ? "created" : "edited";
		boolean success = organizationInfoFormFacade.save(organizationInfoForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, organizationInfoForm.getUserId(),
					organizationInfoForm.getOrganizationInfoFormId(), organizationInfoForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/organization-info-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OrganizationInfoForm> get() {
		return organizationInfoFormFacade.findAll();
	}

	@GET
	@Path("/organization-info-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrganizationInfoForm getLast(@PathParam("org-id") Integer orgId) {
		OrganizationInfoForm form = organizationInfoFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new OrganizationInfoForm();
	}

	@GET
	@Path("/organization-info-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OrganizationInfoForm getById(@PathParam("form-id") Integer formId) {
		return organizationInfoFormFacade.find(formId);
	}

}


