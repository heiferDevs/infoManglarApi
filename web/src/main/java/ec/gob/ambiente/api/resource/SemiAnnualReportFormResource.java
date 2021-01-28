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
import ec.gob.ambiente.infomanglar.forms.model.SemiAnnualReportForm;
import ec.gob.ambiente.infomanglar.forms.services.SemiAnnualReportFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/semi-annual-report-form")
public class SemiAnnualReportFormResource {

	@EJB
	private SemiAnnualReportFormFacade semiAnnualReportFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(SemiAnnualReportForm semiAnnualReportForm) {
		// update Data before save
		for( FileForm fileForm : semiAnnualReportForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setSemiAnnualReportForm(semiAnnualReportForm);
		}
		String changeType = semiAnnualReportForm.getSemiAnnualReportFormId() == null ? "created" : "edited";
		boolean success = semiAnnualReportFormFacade.save(semiAnnualReportForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, semiAnnualReportForm.getUserId(),
					semiAnnualReportForm.getSemiAnnualReportFormId(), semiAnnualReportForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SemiAnnualReportForm> get() {
		return semiAnnualReportFormFacade.findAll();
	}

	@GET
	@Path("/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SemiAnnualReportForm getById(@PathParam("form-id") Integer formId) {
		return semiAnnualReportFormFacade.find(formId);
	}

	@GET
	@Path("/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public SemiAnnualReportForm getLast(@PathParam("org-id") Integer orgId) {
		SemiAnnualReportForm form = semiAnnualReportFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new SemiAnnualReportForm();
	}

}
