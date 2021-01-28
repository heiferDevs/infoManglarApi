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
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.services.ReforestationFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/reforestation-form")
public class ReforestationFormResource {

	@EJB
	private ReforestationFormFacade reforestationFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(ReforestationForm reforestationForm) {
		String changeType = reforestationForm.getReforestationFormId() == null ? "created" : "edited";
		boolean success = reforestationFormFacade.save(reforestationForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, reforestationForm.getUserId(),
					reforestationForm.getReforestationFormId(), reforestationForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReforestationForm> get() {
		return reforestationFormFacade.findAll();
	}


	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ReforestationForm getById(@PathParam("form-id") Integer formId) {
		return reforestationFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public ReforestationForm getLast(@PathParam("org-id") Integer orgId) {
		ReforestationForm form = reforestationFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new ReforestationForm();
	}

}


