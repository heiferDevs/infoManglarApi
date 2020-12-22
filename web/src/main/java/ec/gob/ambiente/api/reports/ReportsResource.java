package ec.gob.ambiente.api.reports;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.api.model.DataResponse;
import ec.gob.ambiente.api.resource.FileResource;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.PdfReportForm;
import ec.gob.ambiente.infomanglar.forms.services.ControlFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.DeforestationFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.DescProjectsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.EconomicIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.EvidenceFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.FishingEffortFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.InfoVedaFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.InvestmentsOrgsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ManagementPlanFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OfficialDocsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OrganizationInfoFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PdfReportFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PlanTrackingFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PricesFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ReforestationFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.SemiAnnualReportFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.SocialIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.TechnicalReportFormFacade;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import ec.gob.ambiente.util.Utility;

@Path("/reports")
public class ReportsResource {

	@EJB
	private UserFacade userFacade;
	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private RolesUserFacade rolesUserFacade;
	@EJB
	private OfficialDocsFormFacade officialDocsFormFacade;
	@EJB
	private ControlFormFacade controlFormFacade;
	@EJB
	private CrabSizeFormFacade crabSizeFormFacade;
	@EJB
	private DeforestationFormFacade deforestationFormFacade;
	@EJB
	private DescProjectsFormFacade descProjectsFormFacade;
	@EJB
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;
	@EJB
	private InfoVedaFormFacade infoVedaFormFacade;
	@EJB
	private PlanTrackingFormFacade planTrackingFormFacade;
	@EJB
	private PricesFormFacade pricesFormFacade;
	@EJB
	private ReforestationFormFacade reforestationFormFacade;
	@EJB
	private ShellSizeFormFacade shellSizeFormFacade;
	@EJB
	private InvestmentsOrgsFormFacade investmentsOrgsFormFacade;
	@EJB
	private ShellCollectionFormFacade shellCollectionFormFacade;
	@EJB
	private CrabCollectionFormFacade crabCollectionFormFacade;
	@EJB
	private EvidenceFormFacade evidenceFormFacade;
	@EJB
	private ManagementPlanFormFacade managementPlanFormFacade;
	@EJB
	private SocialIndicatorsFormFacade socialIndicatorsFormFacade;
	@EJB
	private SemiAnnualReportFormFacade semiAnnualReportFormFacade;
	@EJB
	private TechnicalReportFormFacade technicalReportFormFacade;
	@EJB
	private FishingEffortFormFacade fishingEffortFormFacade;
	@EJB
	private OrganizationInfoFormFacade organizationInfoFormFacade;
	@EJB
	private LimitsFormFacade limitsFormFacade;
	@EJB
	private HistoryChangeFacade historyChangeFacade;
	@EJB
	private PdfReportFormFacade pdfReportFormFacade;

	@GET
	@Path("/{form-type}/{user-type}/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<ReportData> get(
			@PathParam("form-type") String formType,
			@PathParam("user-type") String userType,
			@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs
			) {
		return new ReportsUtil(userFacade, organizationManglarFacade, rolesUserFacade, officialDocsFormFacade, controlFormFacade, crabSizeFormFacade, deforestationFormFacade, descProjectsFormFacade, economicIndicatorsFormFacade, infoVedaFormFacade, planTrackingFormFacade, pricesFormFacade, reforestationFormFacade, shellSizeFormFacade, investmentsOrgsFormFacade, shellCollectionFormFacade, crabCollectionFormFacade, evidenceFormFacade, managementPlanFormFacade, semiAnnualReportFormFacade, technicalReportFormFacade, fishingEffortFormFacade, organizationInfoFormFacade, socialIndicatorsFormFacade)
			.get(formType, userType, userId, orgId, startTs, endTs);
	}

	@GET
	@Path("/csv/{form-type}/{user-type}/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCsv(
			@PathParam("form-type") String formType,
			@PathParam("user-type") String userType,
			@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs
			) {
		String csv = new ReportsUtil(userFacade, organizationManglarFacade, rolesUserFacade, officialDocsFormFacade, controlFormFacade, crabSizeFormFacade, deforestationFormFacade, descProjectsFormFacade, economicIndicatorsFormFacade, infoVedaFormFacade, planTrackingFormFacade, pricesFormFacade, reforestationFormFacade, shellSizeFormFacade, investmentsOrgsFormFacade, shellCollectionFormFacade, crabCollectionFormFacade, evidenceFormFacade, managementPlanFormFacade, semiAnnualReportFormFacade, technicalReportFormFacade, fishingEffortFormFacade, organizationInfoFormFacade, socialIndicatorsFormFacade)
			.getCsv(formType, userType, userId, orgId, startTs, endTs);
		Response.ResponseBuilder response = Response.ok();
		response.header("Content-Disposition", "attachment; filename=\"result.csv\"");
		response.entity(csv);
		return response.build();
	}

	@GET
	@Path("/pdf/{form-type}/{user-type}/{user-id}/{org-id}/{start}/{end}")
	@Produces("application/pdf")
	public Response getPdfFile(
			@PathParam("form-type") String formType,
			@PathParam("user-type") String userType,
			@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs
		) {
		File file = new ReportsUtil(userFacade, organizationManglarFacade, rolesUserFacade, officialDocsFormFacade, controlFormFacade, crabSizeFormFacade, deforestationFormFacade, descProjectsFormFacade, economicIndicatorsFormFacade, infoVedaFormFacade, planTrackingFormFacade, pricesFormFacade, reforestationFormFacade, shellSizeFormFacade, investmentsOrgsFormFacade, shellCollectionFormFacade, crabCollectionFormFacade, evidenceFormFacade, managementPlanFormFacade, semiAnnualReportFormFacade, technicalReportFormFacade, fishingEffortFormFacade, organizationInfoFormFacade, socialIndicatorsFormFacade)
		.getPdf(formType, userType, userId, orgId, startTs, endTs);
		if (!file.exists()) {
	        ResponseBuilder rBuild = Response.status(Response.Status.BAD_REQUEST);
	        String msgError = "Recurso no disponible";
	        return rBuild.type(MediaType.TEXT_PLAIN).entity(msgError).build();
		}
		ResponseBuilder response = Response.ok((Object) file);
		//response.header("Content-Disposition", "attachment; filename=\"" + nameFile + ".pdf\"");
		return response.build();
	}

	@GET
	@Path("/pdf-by-id/{form-type}/{form-id}")
	@Produces("application/pdf")
	public Response getPdfFileById(
			@PathParam("form-type") String formType,
			@PathParam("form-id") Integer formId
		) {
		File file = new ReportsUtil(userFacade, organizationManglarFacade, rolesUserFacade, officialDocsFormFacade, controlFormFacade, crabSizeFormFacade, deforestationFormFacade, descProjectsFormFacade, economicIndicatorsFormFacade, infoVedaFormFacade, planTrackingFormFacade, pricesFormFacade, reforestationFormFacade, shellSizeFormFacade, investmentsOrgsFormFacade, shellCollectionFormFacade, crabCollectionFormFacade, evidenceFormFacade, managementPlanFormFacade, semiAnnualReportFormFacade, technicalReportFormFacade, fishingEffortFormFacade, organizationInfoFormFacade, socialIndicatorsFormFacade)
		.getPdfById(formType, formId);
		if (!file.exists()) {
	        ResponseBuilder rBuild = Response.status(Response.Status.BAD_REQUEST);
	        String msgError = "Recurso no disponible";
	        return rBuild.type(MediaType.TEXT_PLAIN).entity(msgError).build();
		}
		ResponseBuilder response = Response.ok((Object) file);
		//response.header("Content-Disposition", "attachment; filename=\"" + nameFile + ".pdf\"");
		return response.build();
	}

	@POST
	@Path("/pdf-report/save")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public DataResponse save(PdfReportInfo pdfReportInfo) {
		MatricesUtil matricesUtil = new MatricesUtil(pdfReportInfo.getUrlBase(), historyChangeFacade, officialDocsFormFacade, managementPlanFormFacade, evidenceFormFacade, planTrackingFormFacade, organizationInfoFormFacade, crabSizeFormFacade, crabCollectionFormFacade, shellSizeFormFacade, shellCollectionFormFacade, controlFormFacade, reforestationFormFacade, limitsFormFacade, economicIndicatorsFormFacade);
		File file = matricesUtil.get(pdfReportInfo.getOrgId(), pdfReportInfo.getStart(), pdfReportInfo.getEnd());
        String fileName = "semestral_" + pdfReportInfo.getOrgId() + "_" + pdfReportInfo.getStart() + ".pdf";
		boolean successSaveFile = FileResource.saveFile(file, fileName);
		if (!successSaveFile) {
			return new DataResponse(DataResponse.ERROR_STATE + " No se guardó el archivo generado");
		}
		PdfReportForm pdfReportForm = getPdfReportForm(pdfReportInfo, fileName);
		boolean success = pdfReportFormFacade.save(pdfReportForm);
		if (success) {
			return new DataResponse(DataResponse.SUCCESS_STATE);
		}
		return new DataResponse(DataResponse.ERROR_STATE + "  No se guardó el registro correctamente");
	}

	private PdfReportForm getPdfReportForm(PdfReportInfo pdfReportInfo, String fileName){
		PdfReportForm pdfReportForm = new PdfReportForm();
		pdfReportForm.setUserId(pdfReportInfo.getUserId());
		pdfReportForm.setOrganizationManglarId(pdfReportInfo.getOrgId());
		pdfReportForm.setFormType("pdf-report-form");
		pdfReportForm.setFormStatus(true);
		String formatDate = "yyyy-MM-dd";
		pdfReportForm.setIsPublished(false);
		pdfReportForm.setIsApproved(false);
		pdfReportForm.setIsWithObservations(false);
		pdfReportForm.setStartDate(Utility.getDate(pdfReportInfo.getStart(), formatDate));
		pdfReportForm.setEndDate(Utility.getDate(pdfReportInfo.getEnd(), formatDate));
		
		// FileForm
		FileForm fileForm = new FileForm();
		String url = pdfReportInfo.getUrlBase() + "rest/file/documents/" + fileName;
		fileForm.setStatus(true);
		fileForm.setIdOption("pdfReport");
		fileForm.setName(fileName);
		fileForm.setType("document");
		fileForm.setUrl(url);
		fileForm.setAlfrescoCode("alfresco");
		fileForm.setPdfReportForm(pdfReportForm);
		List<FileForm> fileForms = new ArrayList<>();	
		fileForms.add(fileForm);
		pdfReportForm.setFileForms(fileForms);
		return pdfReportForm;
	}

}


class PdfReportInfo{

	@Getter
	@Setter
	private String urlBase;

	@Getter
	@Setter
	private Long start, end;

	@Getter
	@Setter
	private Integer orgId, userId;

}