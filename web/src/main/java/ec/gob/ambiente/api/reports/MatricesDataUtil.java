package ec.gob.ambiente.api.reports;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import ec.gob.ambiente.enlisy.dao.AbstractFacade;
import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.OrganizationInfoForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.model.Agreement;
import ec.gob.ambiente.infomanglar.model.EvidenceActivity;
import ec.gob.ambiente.infomanglar.model.HistoryChange;
import ec.gob.ambiente.infomanglar.model.OrgMapping;
import ec.gob.ambiente.infomanglar.model.PlanInfo;
import ec.gob.ambiente.infomanglar.model.PlannedActivity;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;
import ec.gob.ambiente.util.Utility;

public class MatricesDataUtil {
	
	final String SI = "Si";
	final String NO = "No";
	final String NO_URL = ""; // NO_URL
	final String NO_INFO = ""; // NO_INFO
	final String DATE_FORMAT = "dd/MM/yyyy";
	final String JOIN_SEPARATOR = ", ";

	@Getter
	@Setter
	private String urlBase;
	
	public MatricesDataUtil(String urlBase) {
		super();
		this.urlBase = urlBase;
	}

	public String getPeriod(Date start, Date end) {
		String startFormat = new SimpleDateFormat(DATE_FORMAT).format(start);
		String endFormat = new SimpleDateFormat(DATE_FORMAT).format(end);
		return startFormat + " - " + endFormat;
	}

	public String getDate(){
		return new SimpleDateFormat(DATE_FORMAT).format(new Date());
	}

	public String getOrgName() {
		return null;
	}

	public String getContactPresident(OfficialDocsForm officialDocsForm) {
		if (officialDocsForm == null) return NO_INFO;
		return officialDocsForm.getPhone() + " " + officialDocsForm.getEmail();
	}

	public String getPrincipalActivity(EconomicIndicatorsForm economicIndicatorsForm) {
		if (economicIndicatorsForm == null) return NO_INFO;
		return economicIndicatorsForm.getPrincipalActivity();
	}

	public String getDirectorsChange(OfficialDocsForm officialDocsForm, Long start, Long end) {
		if (officialDocsForm == null) return NO_INFO;
		boolean hasChange = isInRange(officialDocsForm.getDirectorsRegisterDate(), start, end);
		if (hasChange) {
			return SI + getUrl(officialDocsForm.getFileForms(), "directorsRegister");
		}
		return NO;
	}

	public String getPartnersChange(OfficialDocsForm officialDocsForm, Long start, Long end) {
		if (officialDocsForm == null) return NO_INFO;
		boolean hasChange = isInRange(officialDocsForm.getPartnersListDate(), start, end);
		if (hasChange) {
			return SI + getUrl(officialDocsForm.getFileForms(), "partnersDirectorsList");
		}
		return NO;
	}

	public String getRegulationChange(OfficialDocsForm officialDocsForm, Long start, Long end) {
		if (officialDocsForm == null) return NO_INFO;
		boolean hasChange = isInRange(officialDocsForm.getInternalRegulationDate(), start, end);
		if (hasChange) {
			return SI + getUrl(officialDocsForm.getFileForms(), "internalRegulation");
		}
		return NO;
	}

	public String getAgreements(OfficialDocsForm officialDocsForm, Long start, Long end) {
		if (officialDocsForm == null) return NO_INFO;
		List<String> accepted = Arrays.asList(new String[] {"Asistencia técnica", "Cooperación"});
		List<Agreement> agreements = officialDocsForm.getAgreements();
		String result = "";
		for (Agreement agreement : agreements) {
			boolean passType = accepted.indexOf(agreement.getAgreementType()) >= 0;
			boolean passTime = isInRange(agreement.getStartAgreementDate(), agreement.getEndAgreementDate(), start, end);
			if (passType && passTime) {
				String startAgreement = new SimpleDateFormat(DATE_FORMAT).format(Utility.getDate(agreement.getStartAgreementDate()));
				String endAgreement = new SimpleDateFormat(DATE_FORMAT).format(Utility.getDate(agreement.getEndAgreementDate()));
				String lineBreak = result.equals("") ? "" : "<br/>";
				result += lineBreak + agreement.getAgreementType() + " " + startAgreement + " - " + endAgreement +
						getUrl(agreement.getFileForms(), "agreement");
			}
		}
		return "".equals(result) ? NO : result;
	}

	public String getIfChanges(ManagementPlanForm managementPlanForm, HistoryChangeFacade historyChangeFacade, Date start, Date end) {
		if (managementPlanForm == null) return NO_INFO;
		boolean hasChange = hasChange(historyChangeFacade, managementPlanForm.getFormType(), managementPlanForm.getManagementPlanFormId(), start, end);
		if (hasChange) {
			return SI;
		}
		return NO;
	}

	private String getUrlPdfReport(String formType, String userType, String userId, Integer orgId, Long startLong, Long endLong){
		String url = urlBase + "rest/reports/pdf/" + formType + "/" + userType + "/" + userId + "/" + orgId + "/" + startLong + "/" + endLong;
		return " <a href=\"" + url + "\">link</a>";
	}

	private String getUrlPdfReport(String formType, Integer formId){
		String url = urlBase + "rest/reports/pdf-by-id/" + formType + "/" + formId;
		return " <a href=\"" + url + "\">link</a>";
	}

	private String getUrl(List<FileForm> fileForms, String idOption){
		FileForm file = getFile(fileForms, idOption);
		return file == null ? "<br/>" + NO_URL :
			" <a href=\"" + file.getUrl() + "\">link</a>";
	}

	private String getUrlAllLink(List<FileForm> fileForms, String idOption){
		FileForm file = getFile(fileForms, idOption);
		return file == null ? "<br/>" + NO_URL :
			"<br/><span style='color: blue; font-size: 11px'>" + file.getUrl() + "</span>";
	}

	private FileForm getFile(List<FileForm> fileForms, String idOption){
		for(FileForm file : fileForms) {
			if (idOption.equals(file.getIdOption())) return file;
		}
		return null;
	}

	private boolean isInRange(String dateStr, Long start, Long end){
		Long dChange = Utility.getDate(dateStr).getTime();
		return dChange >= start && dChange <= end;		
	}

	private boolean isInRange(String dateStart, String dateEnd, Long start, Long end){
		Long dStart = Utility.getDate(dateStart).getTime();
		Long dEnd = Utility.getDate(dateEnd).getTime();
		return dEnd >= end && dStart < end;		
	}

	private boolean hasChange(HistoryChangeFacade historyChangeFacade, String formType, Integer formId, Date start, Date end){
		List<HistoryChange> changes = historyChangeFacade.findByForm(formType, formId, start, end);
		return changes.size() > 0;
	}

	public String getIndicators(PlanInfo planInfo) {
		if (planInfo == null) return "";
		List<String> indicators = new ArrayList<>();
		for (PlanInfo indicatorsPlan: planInfo.getPlansInfo()) {
			if (indicatorsPlan.getInfo() != null) {
				indicators.add(indicatorsPlan.getInfo());
			}
		}
		return AbstractFacade.join(indicators, JOIN_SEPARATOR);
	}

	public String getActivityDesc(String activity, List<EvidenceForm> evidenceForms) {
		List<String> result = new ArrayList<>();
		for (EvidenceForm evidenceForm: evidenceForms) {
			if (activity.equals(evidenceForm.getActivity())) {
				result.add(evidenceForm.getActivityDesc());
			}
		}
		return AbstractFacade.join(result, JOIN_SEPARATOR);
	}

	public String getObservations(String activity, List<EvidenceForm> evidenceForms) {
		List<String> result = new ArrayList<>();
		for (EvidenceForm evidenceForm: evidenceForms) {
			if (activity.equals(evidenceForm.getActivity())) {
				result.add(evidenceForm.getActivityResults());
			}
		}
		return AbstractFacade.join(result, JOIN_SEPARATOR);
	}

	public String getEvidences(String activity, List<EvidenceForm> evidenceForms) {
		List<String> result = new ArrayList<>();
		for (EvidenceForm evidenceForm: evidenceForms) {
			if (activity.equals(evidenceForm.getActivity())) {
				String url = getUrlPdfReport("evidence-form", evidenceForm.getEvidenceFormId());
				result.add(url);
			}
		}
		return AbstractFacade.join(result, "<br/>");
	}

	public String getPercentage(String activity, PlanTrackingForm planTrackingForm) {
		if (planTrackingForm == null) return "";
		for (PlannedActivity plannedActivity : planTrackingForm.getPlannedActivities()) {
			if (activity.equals(plannedActivity.getActivityDescription())) {
				return plannedActivity.getAverageDone() + "%";
			}
		}
		return "";
	}

	public String getUrlReglamento(OfficialDocsForm officialDocsForm) {
		if (officialDocsForm == null) return NO_URL;
		return getUrlAllLink(officialDocsForm.getFileForms(), "internalRegulation") +
				"<br/> <span>Si fuera necesario monitorear los artículos se debería ingresar el reglamento como un programa más del plan de manejo y los artículos como actividades</span>";
	}

	public String[] getRow(OrganizationInfoForm organizationInfoForm, String keyValue) {
		String question = getQuestion(keyValue);
		if (organizationInfoForm == null) return new String[]{question, "", "", "", "", ""};
		Map<String, Boolean> answers = getMapAnswer(organizationInfoForm);
		Map<String, String> descs = getMapDesc(organizationInfoForm);
		String si = answers.get(keyValue) == null ? "" : (answers.get(keyValue) ? "X" : "");
		String no = answers.get(keyValue) == null ? "" : (answers.get(keyValue) ? "" : "X");
		String desc = descs.get(keyValue) == null ? "" : descs.get(keyValue);
		String evidence = getEvidenceUrl(organizationInfoForm, keyValue);
		String observation = "";
		return new String[]{question, si, no, desc, evidence, observation};
	}

	private String getEvidenceUrl(OrganizationInfoForm organizationInfoForm, String keyValue){
		List<FileForm> fileForms = organizationInfoForm.getFileForms();
		switch(keyValue) {
			case "cumpleReglaNoEnajenar": return getUrl(fileForms, keyValue + "Evidencia");
			case "cumpleReglaNoAmpliar": return getUrl(fileForms, keyValue + "Evidencia");
			case "cumpleReglaBuenaVecindad": return getUrl(fileForms, keyValue + "Evidencia");
			case "pescadoresIndependientes": return getUrl(fileForms, keyValue + "Evidencia");
			case "pescadoresDeOtrasOrgs": return getUrl(fileForms, keyValue + "Evidencia");
			case "denuncioPescadoresNoAutorizados": return getUrl(fileForms, keyValue + "Evidencia");
			case "todosRecurosBioacuaticos": return getUrl(fileForms, keyValue + "Evidencia");
			case "pescaFueraDelManglar": return getUrl(fileForms, keyValue + "Evidencia");
			case "actividadesDeCria": return getUrl(fileForms, keyValue + "Evidencia");
			case "cumpleNormasCria": return getUrl(fileForms, keyValue + "Evidencia");
			case "devuelveIndividuosDecomisados": return getUrl(fileForms, keyValue + "Evidencia");
			case "actividadesTurismo": return getUrl(fileForms, keyValue + "Evidencia");
			case "normativaTuristica": return getUrl(fileForms, keyValue + "Evidencia");
			case "cuentaSocioManglar": return getUrl(fileForms, keyValue + "Evidencia");
			case "actividadesEducacionAmbiental": return getUrl(fileForms, keyValue + "Evidencia");
			case "actividadesInvestigacion": return getUrl(fileForms, keyValue + "Evidencia");
			case "cuentaConPermisos": return getUrl(fileForms, keyValue + "Evidencia");
			case "evidencioMuerteArboles": return getUrl(fileForms, keyValue + "Evidencia");
			case "evidencioMuerteBioacuaticos": return getUrl(fileForms, keyValue + "Evidencia");
			case "evidencioMuerteFlora": return getUrl(fileForms, keyValue + "Evidencia");
			case "evidencioMuerteFauna": return getUrl(fileForms, keyValue + "Evidencia");
			case "cambiosEnAgua": return getUrl(fileForms, keyValue + "Evidencia");
			case "cambiosSuelo": return getUrl(fileForms, keyValue + "Evidencia");
			case "cambiosClima": return getUrl(fileForms, keyValue + "Evidencia");
			case "cambiosTemperatura": return getUrl(fileForms, keyValue + "Evidencia");
		}
		return "";
	}

	private String getQuestion(String keyValue){
		switch(keyValue) {
			case "cumpleReglaNoEnajenar": return "¿La organización cumple con la regla de no vender, renunciar o dividir el área bajo custodia?";
			case "cumpleReglaNoAmpliar": return "¿La organización cumple con la regla de no ampliar el área de la custodia?";
			case "cumpleReglaBuenaVecindad": return "¿Las organizaciones firmantes del acuerdo de buena vecindad respete las reglas establecidos en el acuerdo de buena vecindad?";
			case "pescadoresIndependientes": return "¿Se han encontrado pescadores independientes en su área bajo custodia?";
			case "pescadoresDeOtrasOrgs": return "¿Se han encontrado pescadores de otras organizaciones con acuerdo en su área bajo custodia?";
			case "denuncioPescadoresNoAutorizados": return "¿Se ha puesto una denuncia por el ingreso no autorizado de pescadores que no son socios de la organización al área bajo custodia?";
			case "todosRecurosBioacuaticos": return "¿Todos los socios realizan la actividad extractiva de recursos bioacuáticos del manglar?";
			case "pescaFueraDelManglar": return "¿Los socios de la organización realizan actividades de pesca por fuera del manglar custodiado?";
			case "actividadesDeCria": return "¿La organización realizó actividades de Cría, engorde o cultivo?";
			case "cumpleNormasCria": return "¿La organización cumple con la normativa ambiental para la cría, engorde o cultivo de especies bioacuáticos?";
			case "devuelveIndividuosDecomisados": return "¿Se devuelve los individuos decomisados por incumplimiento de normativa de pesca a espacios de cría y engorde?";
			case "actividadesTurismo": return "¿La organización realizó actividades de turismo ecológico?";
			case "normativaTuristica": return "¿La organización cumple con la normativa turística?";
			case "cuentaSocioManglar": return "¿La asociación cuenta con Socio manglar?";
			case "actividadesEducacionAmbiental": return "¿La organización realizó o participó en actividades de Educación Ambiental?";
			case "actividadesInvestigacion": return "¿La organización realizó o participó en actividades de investigación?";
			case "cuentaConPermisos": return "¿La investigación cuenta con los debidos permisos?";
			case "evidencioMuerteArboles": return "¿Se evidenció muerte, enfermedad o alguna otra degradación de los árboles de manglar en el área bajo custodia?";
			case "evidencioMuerteBioacuaticos": return "¿Se evidenció muerte, enfermedad o alguna otra degradación de las especies bioacuáticos en el área bajo custodia (conchas y cangrejos)?";
			case "evidencioMuerteFlora": return "¿Se evidenció muerte, enfermedad o alguna otra degradación de otras especies de flora en el área bajo custodia?";
			case "evidencioMuerteFauna": return "¿Se evidenció muerte, enfermedad o alguna otra degradación de otras especies de fauna en el área bajo custodia?";
			case "cambiosEnAgua": return "¿Se evidenció cambios en el color, olor o temperatura del agua?";
			case "cambiosSuelo": return "¿Se evidenció cambios en el color, olor o textura del suelo del manglar?";
			case "cambiosClima": return "¿Se evidenció anomalías en el clima como lluvias más fuertes que lo normal, épocas más secas que lo normal?";
			case "cambiosTemperatura": return "¿Se evidenció anomalías en el clima como temperaturas más fríos o calientes que lo normal?";       
		}
		return "noQuestion";
	}

	private Map<String, String> getMapDesc(OrganizationInfoForm organizationInfoForm){
		Map<String, String> vars = new HashMap<>();
		if (organizationInfoForm == null) return vars;
		vars.put("cumpleReglaNoEnajenar", organizationInfoForm.getCumpleReglaNoEnajenarDesc());
		vars.put("cumpleReglaNoAmpliar", organizationInfoForm.getCumpleReglaNoAmpliarDesc());
		vars.put("cumpleReglaBuenaVecindad", organizationInfoForm.getCumpleReglaBuenaVecindadDesc());
		vars.put("pescadoresIndependientes", organizationInfoForm.getPescadoresIndependientesDesc());
		vars.put("pescadoresDeOtrasOrgs", organizationInfoForm.getPescadoresDeOtrasOrgsDesc());
		vars.put("denuncioPescadoresNoAutorizados", organizationInfoForm.getDenuncioPescadoresNoAutorizadosDesc());
		vars.put("todosRecurosBioacuaticos", organizationInfoForm.getTodosRecurosBioacuaticosDesc());
		vars.put("pescaFueraDelManglar", organizationInfoForm.getPescaFueraDelManglarDesc());
		vars.put("actividadesDeCria", organizationInfoForm.getActividadesDeCriaDesc());
		vars.put("cumpleNormasCria", organizationInfoForm.getCumpleNormasCriaDesc());
		vars.put("devuelveIndividuosDecomisados", organizationInfoForm.getDevuelveIndividuosDecomisadosDesc());
		vars.put("actividadesTurismo", organizationInfoForm.getActividadesTurismoDesc());
		vars.put("normativaTuristica", organizationInfoForm.getNormativaTuristicaDesc());
		vars.put("cuentaSocioManglar", organizationInfoForm.getCuentaSocioManglarDesc());
		vars.put("actividadesEducacionAmbiental", organizationInfoForm.getActividadesEducacionAmbientalDesc());
		vars.put("actividadesInvestigacion", organizationInfoForm.getActividadesInvestigacionDesc());
		vars.put("cuentaConPermisos", organizationInfoForm.getCuentaConPermisosDesc());
		vars.put("evidencioMuerteArboles", organizationInfoForm.getEvidencioMuerteArbolesDesc());
		vars.put("evidencioMuerteBioacuaticos", organizationInfoForm.getEvidencioMuerteBioacuaticosDesc());
		vars.put("evidencioMuerteFlora", organizationInfoForm.getEvidencioMuerteFloraDesc());
		vars.put("evidencioMuerteFauna", organizationInfoForm.getEvidencioMuerteFaunaDesc());
		vars.put("cambiosEnAgua", organizationInfoForm.getCambiosEnAguaDesc());
		vars.put("cambiosSuelo", organizationInfoForm.getCambiosSueloDesc());
		vars.put("cambiosClima", organizationInfoForm.getCambiosClimaDesc());
		vars.put("cambiosTemperatura", organizationInfoForm.getCambiosTemperaturaDesc());
		return vars;
	}

	private Map<String, Boolean> getMapAnswer(OrganizationInfoForm organizationInfoForm){
		Map<String, Boolean> vars = new HashMap<>();
		if (organizationInfoForm == null) return vars;
		vars.put("cumpleReglaNoEnajenar", organizationInfoForm.getCumpleReglaNoEnajenar());
		vars.put("cumpleReglaNoAmpliar", organizationInfoForm.getCumpleReglaNoAmpliar());
		vars.put("cumpleReglaBuenaVecindad", organizationInfoForm.getCumpleReglaBuenaVecindad());
		vars.put("pescadoresIndependientes", organizationInfoForm.getPescadoresIndependientes());
		vars.put("pescadoresDeOtrasOrgs", organizationInfoForm.getPescadoresDeOtrasOrgs());
		vars.put("denuncioPescadoresNoAutorizados", organizationInfoForm.getDenuncioPescadoresNoAutorizados());
		vars.put("todosRecurosBioacuaticos", organizationInfoForm.getTodosRecurosBioacuaticos());
		vars.put("pescaFueraDelManglar", organizationInfoForm.getPescaFueraDelManglar());
		vars.put("actividadesDeCria", organizationInfoForm.getActividadesDeCria());
		vars.put("cumpleNormasCria", organizationInfoForm.getCumpleNormasCria());
		vars.put("devuelveIndividuosDecomisados", organizationInfoForm.getDevuelveIndividuosDecomisados());
		vars.put("actividadesTurismo", organizationInfoForm.getActividadesTurismo());
		vars.put("normativaTuristica", organizationInfoForm.getNormativaTuristica());
		vars.put("cuentaSocioManglar", organizationInfoForm.getCuentaSocioManglar());
		vars.put("actividadesEducacionAmbiental", organizationInfoForm.getActividadesEducacionAmbiental());
		vars.put("actividadesInvestigacion", organizationInfoForm.getActividadesInvestigacion());
		vars.put("cuentaConPermisos", organizationInfoForm.getCuentaConPermisos());
		vars.put("evidencioMuerteArboles", organizationInfoForm.getEvidencioMuerteArboles());
		vars.put("evidencioMuerteBioacuaticos", organizationInfoForm.getEvidencioMuerteBioacuaticos());
		vars.put("evidencioMuerteFlora", organizationInfoForm.getEvidencioMuerteFlora());
		vars.put("evidencioMuerteFauna", organizationInfoForm.getEvidencioMuerteFauna());
		vars.put("cambiosEnAgua", organizationInfoForm.getCambiosEnAgua());
		vars.put("cambiosSuelo", organizationInfoForm.getCambiosSuelo());
		vars.put("cambiosClima", organizationInfoForm.getCambiosClima());
		vars.put("cambiosTemperatura", organizationInfoForm.getCambiosTemperatura());
		return vars;
	}

	public String[] getRowBuenaVecindad(OfficialDocsForm officialDocsForm) {
		String question = "¿La organización ha firmado un acuerdo de buena vecindad con otra organización que cuenta con AUSCM?";
		if (officialDocsForm == null) return new String[]{question, "", "", "", "", ""};
		List<Agreement> agreements = getAgreements(officialDocsForm, "Buena vecindad");
		boolean hasAgreements = agreements.size() > 0;
		String desc = hasAgreements ? "Para revisar los acuerdos establecidos revise el link adjunto" :
			"La organización no ha firmado un acuerdo de buena vecindad con otra organización";
		String si = hasAgreements ? "X" : "";
		String no = hasAgreements ? "" : "X";
		String evidence = getEvidence(agreements);
		return new String[]{question, si, no, desc, evidence, ""};
	}

	private String getEvidence(List<Agreement> agreements){
		List<String> urls  = new ArrayList<>();
		for (Agreement agreement : agreements) {
			String url = getUrl(agreement.getFileForms(), "agreement");
			urls.add(url);
		}
		return AbstractFacade.join(urls, "<br/>");
	}

	private List<Agreement> getAgreements(OfficialDocsForm officialDocsForm, String agreementType){
		List<Agreement> agreements = new ArrayList<>();
		for ( Agreement agreement : officialDocsForm.getAgreements() ){
			if (agreementType.equals(agreement.getAgreementType())) {
				agreements.add(agreement);
			}
		}
		return agreements;
	}

	public String[] getRowRealizoPesca(List<CrabCollectionForm> crabCollectionForms, List<ShellCollectionForm> shellCollectionForms, Integer orgId, Long startLong, Long endLong) {
		String question = "¿La organización realizó  actividades de pesca extractiva de peces e invertebrados?";
		boolean hasCrab = crabCollectionForms.size() > 0;
		boolean hasShell = shellCollectionForms.size() > 0;
		String desc = hasCrab || hasShell ? "Para más información de las actividades de extraccióin de peces e invertebrados revise el link adjunto" :
			"La organización no realiza actividades de pesca extractiva de peces e invertebrados";
		String si = hasCrab || hasShell ? "X" : "";
		String no = hasCrab || hasShell ? "" : "X";
		List<String> evidences = new ArrayList<>();
		if (hasShell) evidences.add("Recolección de Conchas " + getUrlPdfReport("shell-collection-form", "all", "all", orgId, startLong, endLong));
		if (hasCrab) evidences.add("Recolección de Cangrejos " + getUrlPdfReport("crab-collection-form", "all", "all", orgId, startLong, endLong));
		return new String[]{question, si, no, desc, AbstractFacade.join(evidences, "<br/>"), ""};
	}

	public String[] getRowRealizoControl(List<CrabSizeForm> crabSizeForms, List<ShellSizeForm> shellSizeForms, Integer orgId, Long startLong, Long endLong) {
		String question = "¿La organización realiza el Control Participativo y Seguimiento a la Pesquería?";
		boolean hasCrab = crabSizeForms.size() > 0;
		boolean hasShell = shellSizeForms.size() > 0;
		String desc = hasCrab || hasShell ? "Para más información del Control Participativo y Seguimiento a la Pesquería revise el link adjunto" :
			"La organización no realiza Control Participativo y Seguimiento a la Pesquería";
		String si = hasCrab || hasShell ? "X" : "";
		String no = hasCrab || hasShell ? "" : "X";
		List<String> evidences = new ArrayList<>();
		if (hasShell) evidences.add("Talla de Conchas " + getUrlPdfReport("shell-size-form", "all", "all", orgId, startLong, endLong));
		if (hasCrab) evidences.add("Talla de Cangrejos " + getUrlPdfReport("crab-size-form", "all", "all", orgId, startLong, endLong));
		return new String[]{question, si, no, desc, AbstractFacade.join(evidences, "<br/>"), ""};
	}

	public String[] getRowNConchas(List<ShellCollectionForm> shellCollectionForms, Integer orgId, Long startLong, Long endLong) {
		String question = "Aproximado al Número de Conchas recolectados al día por recolector";
		boolean hasShell = shellCollectionForms.size() > 0;
		String desc = getRangeShell(getAverageShell(shellCollectionForms));
		String evidence = hasShell ? "Recolección de Conchas " + getUrlPdfReport("shell-collection-form", "all", "all", orgId, startLong, endLong) : "";
		return new String[]{question, "", "", desc, evidence, ""};
	}

	public String[] getRowNCangrejos(List<CrabCollectionForm> crabCollectionForms, Integer orgId, Long startLong, Long endLong) {
		String question = "Aproximado al Número de Cangrejos recolectados por día por recolector";
		boolean hasCrab = crabCollectionForms.size() > 0;
		String desc = getRangeCrab(getAverageCrab(crabCollectionForms));
		String evidence = hasCrab ? "Recolección de Cangrejos " + getUrlPdfReport("crab-collection-form", "all", "all", orgId, startLong, endLong) : "";
		return new String[]{question, "", "", desc, evidence, ""};
	}

	public String[] getRowRealizaControlYVigilancia(List<ControlForm> controlForms, Integer orgId, Long startLong, Long endLong) {
		String question = "¿La organización realiza acciones de control y vigilancia?";
		boolean hasControl = controlForms.size() > 0;
		String desc = hasControl ? "Para más información del control y vigilancia revise el link adjunto" :
			"La organización no realiza acciones de control y vigilancia";
		String si = hasControl ? "X" : "";
		String no = hasControl ? "" : "X";
		String evidence = !hasControl ? "" : "Control y vigilancia " + getUrlPdfReport("control-form", "all", "all", orgId, startLong, endLong);
		return new String[]{question, si, no, desc, evidence, ""};
	}

	public String[] getRowRealizoReforestacion(List<ReforestationForm> reforestationForms, Integer orgId, Long startLong, Long endLong) {
		String question = "¿La organización realizó actividades de reforestación del manglar?";
		boolean hasReforestation = reforestationForms.size() > 0;
		String desc = hasReforestation ? "Para más información de las actividades de reforestación revise el link adjunto" :
			"La organización no realiza actividades de reforestación";
		String si = hasReforestation ? "X" : "";
		String no = hasReforestation ? "" : "X";
		String evidence = !hasReforestation ? "" : "Reforestación " + getUrlPdfReport("reforestation-form", "all", "all", orgId, startLong, endLong);
		return new String[]{question, si, no, desc, evidence, ""};
	}

	private int getAverageShell(List<ShellCollectionForm> shellCollectionForms){
		if (shellCollectionForms.size() == 0) return 0;
		int count = 0;
	    double value = 0;
	    for (ShellCollectionForm shellCollectionForm : shellCollectionForms) {
	    	if (shellCollectionForm.getCollectedGreaterCount() != null) {
	    		count++;
	    		value += new Double(shellCollectionForm.getCollectedGreaterCount());
	    	}
	    }
	    int average = (int) Math.round( value / new Double(count));
	    return average;
	}

	private int getAverageCrab(List<CrabCollectionForm> crabCollectionForms){
		if (crabCollectionForms.size() == 0) return 0;
		int count = 0;
	    double value = 0;
	    for (CrabCollectionForm crabCollectionForm : crabCollectionForms) {
	    	if (crabCollectionForm.getCollectedGreaterCount() != null) {
	    		count++;
	    		value += new Double(crabCollectionForm.getCollectedGreaterCount());
	    	}
	    }
	    int average = (int) Math.round( value / new Double(count));
	    return average;
	}

	public String getRangeShell(int average){
		if (average == 0) return "No realiza recolección de conchas";
		if (average < 25) return "Menor de 25";
		if (average < 50) return "Entre 25 y 50";
		if (average < 100) return "Entre 50 y 100";
		if (average < 150) return "Entre 100 y 150";
		if (average < 200) return "Entre 150 y 200";
		if (average < 300) return "Entre 200 y 300";
		if (average < 400) return "Entre 300 y 400";
		return "Mayor a 400";
	}

	public String getRangeCrab(double average){
		if (average == 0) return "No realiza recolección de cangrejos";
		if (average < 20) return "Menor de 20";
		if (average < 30) return "Entre 20 y 30";
		if (average < 40) return "Entre 30 y 40";
		if (average < 60) return "Entre 40 y 60";
		if (average < 80) return "Entre 60 y 80";
		if (average < 100) return "Entre 80 y 100";
		if (average < 150) return "Entre 100 y 150";
		return "Mayor a 150";
	}

	public String getLimitsChange(LimitsForm limitsForm, Integer orgId, Long start, Long end) {
		if (limitsForm == null || limitsForm.getOrgsMapping().size() == 0) return NO_INFO;
		for (OrgMapping orgMapping : limitsForm.getOrgsMapping()) {
			if (orgId.equals(orgMapping.getOrganizationId())) {
				boolean hasChange = isInRange(orgMapping.getMappingDate(), start, end);
				if (hasChange) {
					return SI + getUrl(orgMapping.getFileForms(), "fileGeoJson");
				}
			}
		}
		return NO;
	}

}
