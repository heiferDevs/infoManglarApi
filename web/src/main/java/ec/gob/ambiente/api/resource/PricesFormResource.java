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
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.services.PricesFormFacade;
import ec.gob.ambiente.infomanglar.model.HistoryChange;
import ec.gob.ambiente.infomanglar.model.PriceDaily;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/prices-form")
public class PricesFormResource {

	@EJB
	private PricesFormFacade pricesFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(PricesForm pricesForm) {
		// update Data before save
		for( PriceDaily priceDaily : pricesForm.getPriceDailies()) {
			priceDaily.setPricesForm(pricesForm);
			for( FileForm fileForm : priceDaily.getFileForms()) {
				fileForm.setStatus(true);
				fileForm.setPriceDaily(priceDaily);
			}
		}
		String changeType = pricesForm.getPricesFormId() == null ? "created" : "edited";
		boolean success = pricesFormFacade.save(pricesForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, pricesForm.getUserId(),
					pricesForm.getPricesFormId(), pricesForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PricesForm> get() {
		return pricesFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PricesForm getById(@PathParam("form-id") Integer formId) {
		return pricesFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PricesForm getLast(@PathParam("org-id") Integer orgId) {
		PricesForm form = pricesFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new PricesForm();
	}

}
