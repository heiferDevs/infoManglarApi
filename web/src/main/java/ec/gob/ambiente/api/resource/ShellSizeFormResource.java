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
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.SizeForm;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/shell-size-form")
public class ShellSizeFormResource {

	@EJB
	private ShellSizeFormFacade shellSizeFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ShellSizeForm shellSizeForm) {
		for( SizeForm sizeForm : shellSizeForm.getSizeForms()) {
			sizeForm.setShellSizeForm(shellSizeForm);
		}
		String changeType = shellSizeForm.getShellSizeFormId() == null ? "created" : "edited";
		boolean success = shellSizeFormFacade.save(shellSizeForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, shellSizeForm.getUserId(),
					shellSizeForm.getShellSizeFormId(), shellSizeForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ShellSizeForm> get() {
		return shellSizeFormFacade.findAll();
	}


	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShellSizeForm getById(@PathParam("form-id") Integer formId) {
		return shellSizeFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ShellSizeForm getLast(@PathParam("org-id") Integer orgId) {
		ShellSizeForm form = shellSizeFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new ShellSizeForm();
	}

}


