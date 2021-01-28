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
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.services.EconomicIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/economic-indicators-form")
public class EconomicIndicatorsFormResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(EconomicIndicatorsForm economicIndicatorsForm) {
		String changeType = economicIndicatorsForm.getEconomicIndicatorsFormId() == null ? "created" : "edited";
		boolean success = economicIndicatorsFormFacade.save(economicIndicatorsForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, economicIndicatorsForm.getUserId(),
					economicIndicatorsForm.getEconomicIndicatorsFormId(), economicIndicatorsForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<EconomicIndicatorsForm> get() {
		return economicIndicatorsFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EconomicIndicatorsForm getById(@PathParam("form-id") Integer formId) {
		return economicIndicatorsFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public EconomicIndicatorsForm getLast(@PathParam("org-id") Integer orgId) {
		EconomicIndicatorsForm form = economicIndicatorsFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new EconomicIndicatorsForm();
	}

}


