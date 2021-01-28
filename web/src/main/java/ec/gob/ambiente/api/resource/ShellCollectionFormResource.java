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
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/shell-collection-form")
public class ShellCollectionFormResource {

	@EJB
	private ShellCollectionFormFacade shellCollectionFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ShellCollectionForm shellCollectionForm) {
		String changeType = shellCollectionForm.getShellCollectionFormId() == null ? "created" : "edited";
		boolean success = shellCollectionFormFacade.save(shellCollectionForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, shellCollectionForm.getUserId(),
					shellCollectionForm.getShellCollectionFormId(), shellCollectionForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShellCollectionForm> get() {
		return shellCollectionFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShellCollectionForm getById(@PathParam("form-id") Integer formId) {
		return shellCollectionFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}/{user-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShellCollectionForm getLast(@PathParam("org-id") Integer orgId, @PathParam("user-id") Integer userId) {
		ShellCollectionForm form = shellCollectionFormFacade.getLastByOrgAndUser(orgId, userId);
		if (form != null) return form;
		return new ShellCollectionForm();
	}

}
