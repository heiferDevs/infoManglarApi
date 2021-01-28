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
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.services.OfficialDocsFormFacade;
import ec.gob.ambiente.infomanglar.model.Agreement;
import ec.gob.ambiente.infomanglar.model.DocumentProject;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/official-docs-form")
public class OfficialDocsFormResource {

	@EJB
	private OfficialDocsFormFacade officialDocsFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(OfficialDocsForm officialDocsForm) {
		// update Data before save
		for( FileForm fileForm : officialDocsForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setOfficialDocsForm(officialDocsForm);
		}
		// update Data before save
		for( Agreement agreement : officialDocsForm.getAgreements()) {
			agreement.setOfficialDocsForm(officialDocsForm);
			for( FileForm fileForm : agreement.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setAgreement(agreement);
			}
		}
		String changeType = officialDocsForm.getOfficialDocsFormId() == null ? "created" : "edited";
		boolean success = officialDocsFormFacade.save(officialDocsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, officialDocsForm.getUserId(),
					officialDocsForm.getOfficialDocsFormId(), officialDocsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<OfficialDocsForm> get() {
		return officialDocsFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OfficialDocsForm getById(@PathParam("form-id") Integer formId) {
		return officialDocsFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public OfficialDocsForm getLast(@PathParam("org-id") Integer orgId) {
		OfficialDocsForm form = officialDocsFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new OfficialDocsForm();
	}

}
