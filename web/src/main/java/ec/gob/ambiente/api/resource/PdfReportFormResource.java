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
import ec.gob.ambiente.infomanglar.forms.model.PdfReportForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.services.PdfReportFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

@Path("/")
public class PdfReportFormResource {

	@EJB
	private PdfReportFormFacade pdfReportFormFacade;

	@EJB
	private HistoryChangeFacade historyChangeFacade;

	@EJB
	private UserFacade userFacade;

	@POST
	@Path("/pdf-report-form/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(PdfReportForm pdfReportForm) {
		// update Data before save
		for( FileForm fileForm : pdfReportForm.getFileForms()) {
			fileForm.setStatus(true);
			fileForm.setPdfReportForm(pdfReportForm);
		}
		String changeType = pdfReportForm.getPdfReportFormId() == null ? "created" : "edited";
		boolean success = pdfReportFormFacade.save(pdfReportForm);
		if (success) {
			new HistoryChangeUtil().save(historyChangeFacade, userFacade, pdfReportForm.getUserId(),
					pdfReportForm.getPdfReportFormId(), pdfReportForm.getFormType(), changeType);
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE);
	}

	@GET
	@Path("/pdf-report-form-rm/{form-id}")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public DataResponse remove(@PathParam("form-id") Integer formId) {
		PdfReportForm pdfReportForm = pdfReportFormFacade.find(formId);
		pdfReportFormFacade.remove(pdfReportForm);
		return new DataResponse(DataResponse.SUCCESS_STATE);
	}

	@GET
	@Path("/pdf-report-form/get")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PdfReportForm> get() {
		return pdfReportFormFacade.findAll();
	}

	@GET
	@Path("/pdf-report-form/get-by-org/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PdfReportForm> getByOrg(@PathParam("org-id") Integer orgId) {
		return pdfReportFormFacade.getByOrg(orgId);
	}

	@GET
	@Path("/pdf-report-form/get-by-org-published/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<PdfReportForm> getByOrgPublished(@PathParam("org-id") Integer orgId) {
		return pdfReportFormFacade.getByOrgPublished(orgId);
	}

	@GET
	@Path("/pdf-report-form/get-last/{org-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PdfReportForm getLast(@PathParam("org-id") Integer orgId) {
		PdfReportForm form = pdfReportFormFacade.getLastByOrg(orgId);
		if (form != null) return form;
		return new PdfReportForm();
	}

	@GET
	@Path("/pdf-report-form/get/{form-id}")
	@Produces(MediaType.APPLICATION_JSON)
	public PdfReportForm getById(@PathParam("form-id") Integer formId) {
		return pdfReportFormFacade.find(formId);
	}

}


