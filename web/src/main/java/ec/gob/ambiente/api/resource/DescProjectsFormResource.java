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
import ec.gob.ambiente.infomanglar.forms.model.DeforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.services.DescProjectsFormFacade;
import ec.gob.ambiente.infomanglar.model.DocumentProject;
import ec.gob.ambiente.infomanglar.services.EmailNotificationFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationsUserFacade;

@Path("/")
public class DescProjectsFormResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private DescProjectsFormFacade descProjectsFormFacade;

	@EJB
	private EmailNotificationFacade emailNotificationFacade;

	@EJB
	private OrganizationsUserFacade organizationsUserFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@POST
	@Path("/desc-projects-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(DescProjectsForm descProjectsForm) {
		// update Data before save
		for( DocumentProject documentProject : descProjectsForm.getDocumentProjects()) {
			documentProject.setDescProjectsForm(descProjectsForm);
			for( FileForm fileForm : documentProject.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setDocumentProject(documentProject);
			}
		}
		String changeType = descProjectsForm.getDescProjectsFormId() == null ? "created" : "edited";
		boolean success = descProjectsFormFacade.save(descProjectsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, descProjectsForm.getUserId(),
					descProjectsForm.getDescProjectsFormId(), descProjectsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/desc-projects-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<DescProjectsForm> get() {
		return descProjectsFormFacade.findAll();
	}

	@GET
	@Path("/desc-projects-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DescProjectsForm getById(@PathParam("form-id") Integer formId) {
		return descProjectsFormFacade.find(formId);
	}

	@GET
	@Path("/desc-projects-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public DescProjectsForm getLast(@PathParam("org-id") Integer orgId) {
		DescProjectsForm form = descProjectsFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new DescProjectsForm();
	}

}
