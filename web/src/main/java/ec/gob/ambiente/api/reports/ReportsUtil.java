package ec.gob.ambiente.api.reports;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.mapping.MappingUtil;
import ec.gob.ambiente.api.util.ValidateUtil;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.model.RolesUser;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.DeforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.model.FishingEffortForm;
import ec.gob.ambiente.infomanglar.forms.model.InfoVedaForm;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.OrganizationInfoForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.SemiAnnualReportForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.SocialIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.TechnicalReportForm;
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
import ec.gob.ambiente.infomanglar.forms.services.ManagementPlanFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OfficialDocsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OrganizationInfoFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PlanTrackingFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PricesFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ReforestationFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.SemiAnnualReportFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.SocialIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.TechnicalReportFormFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.model.PlanInfo;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import lombok.Getter;
import lombok.Setter;

public class ReportsUtil {

	public static String ALL_FILTER = "all";


	private UserFacade userFacade;
	private OrganizationManglarFacade organizationManglarFacade;

	private RolesUserFacade rolesUserFacade;
	private OfficialDocsFormFacade officialDocsFormFacade;
	private ControlFormFacade controlFormFacade;
	private CrabSizeFormFacade crabSizeFormFacade;
	private DeforestationFormFacade deforestationFormFacade;
	private DescProjectsFormFacade descProjectsFormFacade;
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;
	private InfoVedaFormFacade infoVedaFormFacade;
	private PlanTrackingFormFacade planTrackingFormFacade;
	private PricesFormFacade pricesFormFacade;
	private ReforestationFormFacade reforestationFormFacade;
	private ShellSizeFormFacade shellSizeFormFacade;
	private InvestmentsOrgsFormFacade investmentsOrgsFormFacade;
	private ShellCollectionFormFacade shellCollectionFormFacade;
	private CrabCollectionFormFacade crabCollectionFormFacade;
	private EvidenceFormFacade evidenceFormFacade;
	private ManagementPlanFormFacade managementPlanFormFacade;
	private SocialIndicatorsFormFacade socialIndicatorsFormFacade;
	private SemiAnnualReportFormFacade semiAnnualReportFormFacade;
	private TechnicalReportFormFacade technicalReportFormFacade;
	private FishingEffortFormFacade fishingEffortFormFacade;
	private OrganizationInfoFormFacade organizationInfoFormFacade;

	private CsvUtil csvUtil = new CsvUtil();
	private PdfReportsUtil pdfReportsUtil = new PdfReportsUtil();

	public ReportsUtil(UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade,
			RolesUserFacade rolesUserFacade,
			OfficialDocsFormFacade officialDocsFormFacade,
			ControlFormFacade controlFormFacade,
			CrabSizeFormFacade crabSizeFormFacade,
			DeforestationFormFacade deforestationFormFacade,
			DescProjectsFormFacade descProjectsFormFacade,
			EconomicIndicatorsFormFacade economicIndicatorsFormFacade,
			InfoVedaFormFacade infoVedaFormFacade,
			PlanTrackingFormFacade planTrackingFormFacade,
			PricesFormFacade pricesFormFacade,
			ReforestationFormFacade reforestationFormFacade,
			ShellSizeFormFacade shellSizeFormFacade,
			InvestmentsOrgsFormFacade investmentsOrgsFormFacade,
			ShellCollectionFormFacade shellCollectionFormFacade,
			CrabCollectionFormFacade crabCollectionFormFacade,
			EvidenceFormFacade evidenceFormFacade,
			ManagementPlanFormFacade managementPlanFormFacade,
			SemiAnnualReportFormFacade semiAnnualReportFormFacade,
			TechnicalReportFormFacade technicalReportFormFacade,
			FishingEffortFormFacade fishingEffortFormFacade,
			OrganizationInfoFormFacade organizationInfoFormFacade,
			SocialIndicatorsFormFacade socialIndicatorsFormFacade) {
		super();
		this.userFacade = userFacade;
		this.organizationManglarFacade = organizationManglarFacade;
		this.rolesUserFacade = rolesUserFacade;
		this.officialDocsFormFacade = officialDocsFormFacade;
		this.controlFormFacade = controlFormFacade;
		this.crabSizeFormFacade = crabSizeFormFacade;
		this.deforestationFormFacade = deforestationFormFacade;
		this.descProjectsFormFacade = descProjectsFormFacade;
		this.economicIndicatorsFormFacade = economicIndicatorsFormFacade;
		this.infoVedaFormFacade = infoVedaFormFacade;
		this.planTrackingFormFacade = planTrackingFormFacade;
		this.pricesFormFacade = pricesFormFacade;
		this.reforestationFormFacade = reforestationFormFacade;
		this.shellSizeFormFacade = shellSizeFormFacade;
		this.investmentsOrgsFormFacade = investmentsOrgsFormFacade;
		this.shellCollectionFormFacade = shellCollectionFormFacade;
		this.crabCollectionFormFacade = crabCollectionFormFacade;
		this.evidenceFormFacade = evidenceFormFacade;
		this.managementPlanFormFacade = managementPlanFormFacade;
		this.semiAnnualReportFormFacade = semiAnnualReportFormFacade;
		this.technicalReportFormFacade = technicalReportFormFacade;
		this.fishingEffortFormFacade = fishingEffortFormFacade;
		this.organizationInfoFormFacade = organizationInfoFormFacade;
		this.socialIndicatorsFormFacade = socialIndicatorsFormFacade;
	}

	public String getCsv(String formType, String userType, String userId, String orgId, String startTs, String endTs) {
		switch (formType) {
			case "all":
				List<ReportData> reports = get(formType, userType, userId, orgId, startTs, endTs);
				return csvUtil.getCsvAll(reports);
			default:
				List<?> forms = getForms(formType, userType, userId, orgId, startTs, endTs);
				return csvUtil.getCsv(forms);
		}
	}

	public File getPdf(String formType, String userType, String userId, String orgId, String startTs, String endTs) {
		String nombreReporte = "pdfReport";
		switch (formType) {
			case "all":
				return new PdfUtil().generarFichero("<span>all types not accepted</span>", nombreReporte, true);
			default:
				List<?> forms = getForms(formType, userType, userId, orgId, startTs, endTs);
				String cadenaHtml = pdfReportsUtil.getHtml(forms, getTitle(formType));
				return new PdfUtil().generarFichero(cadenaHtml, nombreReporte, false);
		}
	}

	public File getPdfById(String formType, Integer formId) {
		String nombreReporte = "pdfReport";
		Object form = getForm(formType, formId);
		String cadenaHtml = pdfReportsUtil.getHtml(form, getTitle(formType));
		return new PdfUtil().generarFichero(cadenaHtml, nombreReporte, false);
	}

	public List<ReportData> get(String formType, String userType, String userId, String orgId, String startTs, String endTs) {
		List<ReportData> reports = new ArrayList<>();
		
		// FORMS
		List<OfficialDocsForm> officialDocs = officialDocsFormFacade.findBy("OfficialDocsForm", "official-docs-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ControlForm> controlForms = controlFormFacade.findBy("ControlForm", "control-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<CrabSizeForm> crabSizeForms = crabSizeFormFacade.findBy("CrabSizeForm", "crab-size-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<DeforestationForm> deforestationForms = deforestationFormFacade.findBy("DeforestationForm", "deforestation-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<DescProjectsForm> descProjectsForms = descProjectsFormFacade.findBy("DescProjectsForm", "desc-projects-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<EconomicIndicatorsForm> economicIndicatorsForms = economicIndicatorsFormFacade.findBy("EconomicIndicatorsForm", "economic-indicators-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<InfoVedaForm> infoVedaForms = infoVedaFormFacade.findBy("InfoVedaForm", "info-veda-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<InvestmentsOrgsForm> investmentsOrgsForms = investmentsOrgsFormFacade.findBy("InvestmentsOrgsForm", "investments-orgs-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<PlanTrackingForm> planTrackingForms = planTrackingFormFacade.findBy("PlanTrackingForm", "plan-tracking-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<PricesForm> pricesForms = pricesFormFacade.findBy("PricesForm", "prices-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ReforestationForm> reforestationForms = reforestationFormFacade.findBy("ReforestationForm", "reforestation-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ShellSizeForm> shellSizeForms = shellSizeFormFacade.findBy("ShellSizeForm", "shell-size-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ShellCollectionForm> shellCollectionForms = shellCollectionFormFacade.findBy("ShellCollectionForm", "shell-collection-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<CrabCollectionForm> crabCollectionForms = crabCollectionFormFacade.findBy("CrabCollectionForm", "crab-collection-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<EvidenceForm> evidenceForms = evidenceFormFacade.findBy("EvidenceForm", "evidence-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ManagementPlanForm> managementPlanForms = managementPlanFormFacade.findBy("ManagementPlanForm", "management-plan-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<SocialIndicatorsForm> socialIndicatorsForms = socialIndicatorsFormFacade.findBy("SocialIndicatorsForm", "social-indicators-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<SemiAnnualReportForm> semiAnnualReportForms = getSemiAnnualReportForms(formType, userId, orgId, startTs, endTs);
		List<TechnicalReportForm> technicalReportForms = technicalReportFormFacade.findBy("TechnicalReportForm", "technical-report-form", ALL_FILTER, formType, userId, ALL_FILTER, startTs, endTs);
		List<FishingEffortForm> fishingEffortForms = fishingEffortFormFacade.findBy("FishingEffortForm", "fishing-effort-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<OrganizationInfoForm> organizationInfoForms = organizationInfoFormFacade.findBy("OrganizationInfoForm", "organization-info-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		
		// ADD REPORTS
		for (OfficialDocsForm form : officialDocs) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ControlForm form : controlForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (CrabSizeForm form : crabSizeForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (DeforestationForm form : deforestationForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (DescProjectsForm form : descProjectsForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (EconomicIndicatorsForm form : economicIndicatorsForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (InfoVedaForm form : infoVedaForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (InvestmentsOrgsForm form : investmentsOrgsForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (PlanTrackingForm form : planTrackingForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (PricesForm form : pricesForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ReforestationForm form : reforestationForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ShellSizeForm form : shellSizeForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ShellCollectionForm form : shellCollectionForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (CrabCollectionForm form : crabCollectionForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (EvidenceForm form : evidenceForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ManagementPlanForm form : managementPlanForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (SocialIndicatorsForm form : socialIndicatorsForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (SemiAnnualReportForm form : semiAnnualReportForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (TechnicalReportForm form : technicalReportForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (FishingEffortForm form : fishingEffortForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (OrganizationInfoForm form : organizationInfoForms) reports.add(new ReportData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));

		List<ReportData> reportsFilterByUser = filterUserType(reports, userType);
		return sortByDate(reportsFilterByUser);
	}

	private List<ReportData> filterUserType(List<ReportData> reports, String userType){
		if (ALL_FILTER.equals(userType)) {
			return reports;
		}
		List<ReportData> results = new ArrayList<>();
		for (ReportData reportData : reports) {
			List<String> formsByUserType = getFormsTypesByUserType(userType);
			if (formsByUserType.indexOf(reportData.getTypeForm()) >= 0) {
				results.add(reportData);
			}
		}
		return results;
	}

	private List<String> getFormsTypesByUserType(String userType){
		switch(userType){
			case "socio":
				return Arrays.asList(
						new String[] { "crab-collection-form", "shell-collection-form", "crab-size-form", "shell-size-form" });
			case "org":
				return Arrays.asList(
						new String[] { "fishing-effort-form", "evidence-form", "control-form", "deforestation-form",
								"economic-indicators-form", "info-veda-form", "investments-orgs-form", "plan-tracking-form",
								"desc-projects-form", "reforestation-form" });
			case "mae":
				return Arrays.asList(new String[] { "semi-annual-report-form" });
			case "inp":
				return Arrays.asList(new String[] { "technical-report-form" });

		}
		return new ArrayList<String>();
	}

	private String getUserType(Integer userId){
		List<RolesUser> rolesUsers = rolesUserFacade.findByUserId(userId);
		for ( RolesUser rolesUser : rolesUsers ) {
			Role role = rolesUser.getRole();
			if ( new ValidateUtil().passRole(role) ) {
				return AccessController.getTypeUser(role.getRoleName());
			}
		}
		return null;
	}

	private List<ReportData> sortByDate(List<ReportData> reports) {
		Collections.sort(reports, new Comparator<ReportData>() {
		    @Override
		    public int compare(ReportData r1, ReportData r2) {
		        return r1.getCreatedAt() > r2.getCreatedAt() ? 1 :
		        	(r1.getCreatedAt() < r2.getCreatedAt()) ? -1 : 0;
		    }
		});
		return reports;
	}

	private String getOrgName(String orgId) {
		if (ALL_FILTER.equals(orgId)) return ALL_FILTER;
		int orgIdInt = MappingUtil.tryParse(orgId, -1);
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(orgIdInt);
		if (organizationManglar == null) return ALL_FILTER;
		return organizationManglar.getOrganizationManglarName();
	}

	private List<SemiAnnualReportForm> getSemiAnnualReportForms(String formType, String userId, String orgId, String startTs, String endTs){
		String orgName = getOrgName(orgId);
		if ("MAE".equals(orgName)) {
			// IF MAE RETURNS ALL FILTER BECAUSE ITS USED BY MAE USERS
			return semiAnnualReportFormFacade.findBy("SemiAnnualReportForm", "semi-annual-report-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		}
		return semiAnnualReportFormFacade.findByCustom("SemiAnnualReportForm", "semi-annual-report-form", ALL_FILTER, formType, userId, orgName, startTs, endTs);
	}

	private List<?> getForms(String formType, String userType, String userId, String orgId, String startTs, String endTs){
		switch(formType){
		case "official-docs-form":
			return officialDocsFormFacade.findBy("OfficialDocsForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "control-form":
			return controlFormFacade.findBy("ControlForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "crab-size-form":
			return crabSizeFormFacade.findBy("CrabSizeForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "deforestation-form":
			return deforestationFormFacade.findBy("DeforestationForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "desc-projects-form":
			return descProjectsFormFacade.findBy("DescProjectsForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "economic-indicators-form":
			return economicIndicatorsFormFacade.findBy("EconomicIndicatorsForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "info-veda-form":
			return infoVedaFormFacade.findBy("InfoVedaForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "investments-orgs-form":
			return investmentsOrgsFormFacade.findBy("InvestmentsOrgsForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "plan-tracking-form":
			return planTrackingFormFacade.findBy("PlanTrackingForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "prices-form":
			return pricesFormFacade.findBy("PricesForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "reforestation-form":
			return reforestationFormFacade.findBy("ReforestationForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "shell-size-form":
			return shellSizeFormFacade.findBy("ShellSizeForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "shell-collection-form":
			return shellCollectionFormFacade.findBy("ShellCollectionForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "crab-collection-form":
			return crabCollectionFormFacade.findBy("CrabCollectionForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "evidence-form":
			return evidenceFormFacade.findBy("EvidenceForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "management-plan-form":
			return managementPlanFormFacade.findBy("ManagementPlanForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "social-indicators-form":
			return socialIndicatorsFormFacade.findBy("SocialIndicatorsForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "technical-report-form":
			return technicalReportFormFacade.findBy("TechnicalReportForm", ALL_FILTER, userId, ALL_FILTER, startTs, endTs);
		case "fishing-effort-form":
			return fishingEffortFormFacade.findBy("FishingEffortForm", ALL_FILTER, userId, orgId, startTs, endTs);
		case "semi-annual-report-form":
			return getSemiAnnualReportForms(formType, userId, orgId, startTs, endTs);
		}
		return new ArrayList<Object>();
	}

	private Object getForm(String formType, Integer formId){
		switch(formType){
		case "official-docs-form":
			return officialDocsFormFacade.find(formId);
		case "control-form":
			return controlFormFacade.find(formId);
		case "crab-size-form":
			return crabSizeFormFacade.find(formId);
		case "deforestation-form":
			return deforestationFormFacade.find(formId);
		case "desc-projects-form":
			return descProjectsFormFacade.find(formId);
		case "economic-indicators-form":
			return economicIndicatorsFormFacade.find(formId);
		case "info-veda-form":
			return infoVedaFormFacade.find(formId);
		case "investments-orgs-form":
			return investmentsOrgsFormFacade.find(formId);
		case "plan-tracking-form":
			return planTrackingFormFacade.find(formId);
		case "prices-form":
			return pricesFormFacade.find(formId);
		case "reforestation-form":
			return reforestationFormFacade.find(formId);
		case "shell-size-form":
			return shellSizeFormFacade.find(formId);
		case "shell-collection-form":
			return shellCollectionFormFacade.find(formId);
		case "crab-collection-form":
			return crabCollectionFormFacade.find(formId);
		case "evidence-form":
			return evidenceFormFacade.find(formId);
		case "management-plan-form":
			return managementPlanFormFacade.find(formId);
		case "social-indicators-form":
			return socialIndicatorsFormFacade.find(formId);
		case "technical-report-form":
			return technicalReportFormFacade.find(formId);
		case "fishing-effort-form":
			return fishingEffortFormFacade.find(formId);
		case "semi-annual-report-form":
			return semiAnnualReportFormFacade.find(formId);
		}
		return null;
	}

	  public static String getTitle(String typeForm) {
		  switch (typeForm) {
		  case "official-docs-form":
				return "Documentos Oficiales";
			case "control-form":
				return "Control";
			case "crab-size-form":
				return "Talla de Cangrejos";
			case "deforestation-form":
				return "Deforestación";
			case "desc-projects-form":
				return "Proyecto";
			case "economic-indicators-form":
				return "Indicadores económicos";
			case "info-veda-form":
				return "Información de Veda";
			case "investments-orgs-form":
				return "Inversiones de Organización";
			case "plan-tracking-form":
				return "Seguimiento Actividades";
			case "prices-form":
				return "Precios";
			case "reforestation-form":
				return "Reforestación";
			case "shell-size-form":
				return "Talla de Conchas";
			case "shell-collection-form":
				return "Recolección de Conchas";
			case "crab-collection-form":
				return "Recolección de Cangrejos";
			case "evidence-form":
				return "Evidencia Informe Semestral";
			case "management-plan-form":
				return "Plan de manejo";
			case "semi-annual-report-form":
				return "Informe Semestral";
			case "technical-report-form":
				return "Informe Técnico";
			case "fishing-effort-form":
				return "Esfuerzo Pezquero";
			case "social-indicators-form":
				return "Información socio";
			case "organization-info-form":
				return "Información organización";
		  }
		  return "NO_TITLE";
	  }

		// RECURSIVE FIND ACTIVITIES
		private static void fillActivities(PlanInfo planInfo, List<String> activities, String typeInfo){
			if (typeInfo.equals(planInfo.getTypeInfo())) {
				if (planInfo.getInfo() != null) activities.add(planInfo.getInfo());
			} else {
				for (PlanInfo planInfoChild : planInfo.getPlansInfo()) {
					fillActivities(planInfoChild, activities, typeInfo);
				}
			}
		}

		public static List<String> getFromManagmentPlanForm(ManagementPlanForm form, String type){
			List<String> activities = new ArrayList<>();
			if (form == null) return activities;
			for (PlanInfo planInfo : form.getPlansInfo()) {
				fillActivities(planInfo, activities, type);
			}
			return activities;
		}

		private static void fillActivitiesPlansInfo(PlanInfo planInfo, List<PlanInfo> activities, String typeInfo){
			if (typeInfo.equals(planInfo.getTypeInfo())) {
				if (planInfo.getInfo() != null) activities.add(planInfo);
			} else {
				for (PlanInfo planInfoChild : planInfo.getPlansInfo()) {
					fillActivitiesPlansInfo(planInfoChild, activities, typeInfo);
				}
			}
		}

		public static List<PlanInfo> getFromManagmentPlanFormPlansInfo(ManagementPlanForm form, String type){
			List<PlanInfo> activities = new ArrayList<>();
			if (form == null) return activities;
			for (PlanInfo planInfo : form.getPlansInfo()) {
				fillActivitiesPlansInfo(planInfo, activities, type);
			}
			return activities;
		}

		public static List<PlanInfo> getActivitiesFromProgram(PlanInfo program) {
			List<PlanInfo> activities = new ArrayList<>();
			for (PlanInfo objetive : program.getPlansInfo()) {
				if (objetive.getInfo() == null) continue;
				for (PlanInfo activity : objetive.getPlansInfo()) {
					if (activity.getInfo() == null) continue;
					activities.add(activity);
				}
			}
			return activities;		
		}

}


class ReportData {

	public ReportData(String title, String subTitle, Integer idForm,
			String typeForm, Long createdAt, String extraInfo,
			Integer userId, String userName, String userType,
			Integer organizationId, String organizationName) {
		super();
		this.title = title;
		this.subTitle = subTitle;
		this.idForm = idForm;
		this.typeForm = typeForm;
		this.createdAt = createdAt;
		this.extraInfo = extraInfo;
		this.userId = userId;
		this.userName = userName;
		this.userType = userType;
		this.organizationId = organizationId;
		this.organizationName = organizationName;
	}

	public ReportData(OfficialDocsForm form, String userType, UserFacade userFacade, OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getOfficialDocsFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(ControlForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Responsable: " + form.getResponsiblePatrullaje();
		this.idForm = form.getControlFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = form.getStartSite() + " - " + form.getEndSite();
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(DeforestationForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Causa: " + form.getProbableCause();
		this.idForm = form.getDeforestationFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "Área: " +  form.getDeforestedArea();
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(InvestmentsOrgsForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Invertido: $" + getInvestmenteValue(form);
		this.idForm = form.getInvestmentsOrgsFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(PricesForm form, String userType, UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getPricesFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(EconomicIndicatorsForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Principal: " + form.getPrincipalActivity();
		this.idForm = form.getEconomicIndicatorsFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "Secundaria: " + form.getAlternativeActivities();
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(CrabSizeForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getCrabSizeFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(ShellSizeForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Tipo: " + form.getShellType();
		this.idForm = form.getShellSizeFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(ReforestationForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Área: " + form.getReforestedArea() + " m2";
		this.idForm = form.getReforestationFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(PlanTrackingForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getPlanTrackingFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(InfoVedaForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getInfoVedaFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(DescProjectsForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = form.getProjectName() != null ? ("Nombre: " + form.getProjectName()) : ("Tipo: "  + form.getProjectType());
		this.idForm = form.getDescProjectsFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = form.getProjectObjective() != null ? ("Objetivo: " + form.getProjectObjective()) : ("Presupuesto: " + form.getBudget());
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(ShellCollectionForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Periodo: " + form.getShellPeriod();
		this.idForm = form.getShellCollectionFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "Horas: " + form.getHoursWorked();
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(CrabCollectionForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Periodo: " + form.getCrabPeriod();
		this.idForm = form.getCrabCollectionFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "Horas: " + form.getHoursWorked();
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(EvidenceForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Actividad: " + form.getActivity();
		this.idForm = form.getEvidenceFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(ManagementPlanForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getManagementPlanFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(SocialIndicatorsForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getSocialIndicatorsFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(SemiAnnualReportForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "Organización: " + form.getOrganizationName();
		this.idForm = form.getSemiAnnualReportFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(TechnicalReportForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getTechnicalReportFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(FishingEffortForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getFishingEffortFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	public ReportData(OrganizationInfoForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getOrganizationInfoFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
	}

	@Getter
	  @Setter
	  private String title;

	  @Getter
	  @Setter
	  private String subTitle;

	  @Getter
	  @Setter
	  private Integer idForm;
	  
	  @Getter
	  @Setter
	  private String typeForm;
	  
	  @Getter
	  @Setter
	  private Long createdAt;
	  
	  @Getter
	  @Setter
	  private String extraInfo;

	  @Getter
	  @Setter
	  private Integer userId;
	  
	  @Getter
	  @Setter
	  private String userName;
	  
	  @Getter
	  @Setter
	  private String userType;

	  @Getter
	  @Setter
	  private Integer organizationId;
	  
	  @Getter
	  @Setter
	  private String organizationName;

	  private double getInvestmenteValue(InvestmentsOrgsForm form) {
		  double result = 0.0;
		  if (form.getSurveillanceControl() != null) result += form.getSurveillanceControl();
		  if (form.getSustainableUse() != null) result += form.getSustainableUse();
		  if (form.getAdministrativesExpenses() != null) result += form.getAdministrativesExpenses();
		  if (form.getSocialActivities() != null) result += form.getSocialActivities();
		  if (form.getMonitoring() != null) result += form.getMonitoring();
		  if (form.getCapacitation() != null) result += form.getCapacitation();
		  if (form.getReforestation() != null) result += form.getReforestation();
		  if (form.getInfrastructure() != null) result += form.getInfrastructure();
		  return result;
	  }

}
