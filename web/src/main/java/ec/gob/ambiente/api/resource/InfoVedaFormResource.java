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
import ec.gob.ambiente.infomanglar.forms.services.InfoVedaFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/info-veda-form")
public class InfoVedaFormResource {

	@EJB
	private InfoVedaFormFacade infoVedaFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(InfoVedaForm infoVedaForm) {
		String changeType = infoVedaForm.getInfoVedaFormId() == null ? "created" : "edited";
		boolean success = infoVedaFormFacade.save(infoVedaForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, infoVedaForm.getUserId(),
					infoVedaForm.getInfoVedaFormId(), infoVedaForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<InfoVedaForm> get() {
		return infoVedaFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InfoVedaForm getById(@PathParam("form-id") Integer formId) {
		return infoVedaFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public InfoVedaForm getLast(@PathParam("org-id") Integer orgId) {
		InfoVedaForm form = infoVedaFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new InfoVedaForm();
	}

}


