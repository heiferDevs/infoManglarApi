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
import ec.gob.ambiente.infomanglar.forms.model.TechnicalReportForm;
import ec.gob.ambiente.infomanglar.forms.services.TechnicalReportFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class TechnicalReportFormResource {

	@EJB
	private TechnicalReportFormFacade technicalReportFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/technical-report-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(TechnicalReportForm technicalReportForm) {
		// update Data before save
		for( FileForm fileForm : technicalReportForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setTechnicalReportForm(technicalReportForm);
		}
		String changeType = technicalReportForm.getTechnicalReportFormId() == null ? "created" : "edited";
		boolean success = technicalReportFormFacade.save(technicalReportForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, technicalReportForm.getUserId(),
					technicalReportForm.getTechnicalReportFormId(), technicalReportForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/technical-report-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<TechnicalReportForm> get() {
		return technicalReportFormFacade.findAll();
	}

	@GET
	@Path("/technical-report-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TechnicalReportForm getById(@PathParam("form-id") Integer formId) {
		return technicalReportFormFacade.find(formId);
	}

	@GET
	@Path("/technical-report-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public TechnicalReportForm getLast(@PathParam("org-id") Integer orgId) {
		TechnicalReportForm form = technicalReportFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new TechnicalReportForm();
	}

}
