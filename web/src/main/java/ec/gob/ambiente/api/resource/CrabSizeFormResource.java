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
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.SizeForm;
import ec.gob.ambiente.infomanglar.forms.services.CrabSizeFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/crab-size-form")
public class CrabSizeFormResource {

	@EJB
	private CrabSizeFormFacade crabSizeFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(CrabSizeForm crabSizeForm) {
		for( SizeForm sizeForm : crabSizeForm.getSizeForms()) {
			sizeForm.setCrabSizeForm(crabSizeForm);
		}
		String changeType = crabSizeForm.getCrabSizeFormId() == null ? "created" : "edited";
		boolean success = crabSizeFormFacade.save(crabSizeForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, crabSizeForm.getUserId(),
					crabSizeForm.getCrabSizeFormId(), crabSizeForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<CrabSizeForm> get() {
		return crabSizeFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CrabSizeForm getById(@PathParam("form-id") Integer formId) {
		return crabSizeFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public CrabSizeForm getLast(@PathParam("org-id") Integer orgId) {
		CrabSizeForm form = crabSizeFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new CrabSizeForm();
	}

}


