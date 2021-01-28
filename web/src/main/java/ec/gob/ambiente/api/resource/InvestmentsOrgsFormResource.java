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
import ec.gob.ambiente.infomanglar.forms.model.InfoVedaForm;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.services.InvestmentsOrgsFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/investments-orgs-form")
public class InvestmentsOrgsFormResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private InvestmentsOrgsFormFacade investmentsOrgsFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(InvestmentsOrgsForm investmentsOrgsForm) {
		String changeType = investmentsOrgsForm.getInvestmentsOrgsFormId() == null ? "created" : "edited";
		boolean success = investmentsOrgsFormFacade.save(investmentsOrgsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, investmentsOrgsForm.getUserId(),
					investmentsOrgsForm.getInvestmentsOrgsFormId(), investmentsOrgsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InvestmentsOrgsForm> get() {
		return investmentsOrgsFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InvestmentsOrgsForm getById(@PathParam("form-id") Integer formId) {
		return investmentsOrgsFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InvestmentsOrgsForm getLast(@PathParam("org-id") Integer orgId) {
		InvestmentsOrgsForm form = investmentsOrgsFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new InvestmentsOrgsForm();
	}

}


