package ec.gob.ambiente.api.reports;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.EvidenceForm;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;
import ec.gob.ambiente.infomanglar.forms.model.ManagementPlanForm;
import ec.gob.ambiente.infomanglar.forms.model.OfficialDocsForm;
import ec.gob.ambiente.infomanglar.forms.model.OrganizationInfoForm;
import ec.gob.ambiente.infomanglar.forms.model.PlanTrackingForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.services.ControlFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.EconomicIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.EvidenceFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ManagementPlanFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OfficialDocsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.OrganizationInfoFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PlanTrackingFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ReforestationFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.model.PlanInfo;
import ec.gob.ambiente.infomanglar.services.HistoryChangeFacade;

public class MatricesUtil {

	private HistoryChangeFacade historyChangeFacade;
	private OfficialDocsFormFacade officialDocsFormFacade;
	private ManagementPlanFormFacade managementPlanFormFacade;
	private EvidenceFormFacade evidenceFormFacade;
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;
	private PlanTrackingFormFacade planTrackingFormFacade;
	private OrganizationInfoFormFacade organizationInfoFormFacade;
	private CrabSizeFormFacade crabSizeFormFacade;
	private CrabCollectionFormFacade crabCollectionFormFacade;
	private ShellSizeFormFacade shellSizeFormFacade;
	private ShellCollectionFormFacade shellCollectionFormFacade;
	private ControlFormFacade controlFormFacade;
	private ReforestationFormFacade reforestationFormFacade;
	private LimitsFormFacade limitsFormFacade;
	
	private TableUtil tableUtil = new TableUtil();
	
	MatricesDataUtil data;
	
	private List<String> matrices;

	public MatricesUtil(
			String urlBase,
			HistoryChangeFacade historyChangeFacade,
			OfficialDocsFormFacade officialDocsFormFacade,
			ManagementPlanFormFacade managementPlanFormFacade,
			EvidenceFormFacade evidenceFormFacade,
			PlanTrackingFormFacade planTrackingFormFacade,
			OrganizationInfoFormFacade organizationInfoFormFacade,
			CrabSizeFormFacade crabSizeFormFacade,
			CrabCollectionFormFacade crabCollectionFormFacade,
			ShellSizeFormFacade shellSizeFormFacade,
			ShellCollectionFormFacade shellCollectionFormFacade,
			ControlFormFacade controlFormFacade,
			ReforestationFormFacade reforestationFormFacade,
			LimitsFormFacade limitsFormFacade,
			EconomicIndicatorsFormFacade economicIndicatorsFormFacade
			) {
		super();
		this.historyChangeFacade = historyChangeFacade;
		this.officialDocsFormFacade = officialDocsFormFacade;
		this.managementPlanFormFacade = managementPlanFormFacade;
		this.evidenceFormFacade = evidenceFormFacade;
		this.planTrackingFormFacade = planTrackingFormFacade;
		this.organizationInfoFormFacade = organizationInfoFormFacade;
		this.crabSizeFormFacade = crabSizeFormFacade;
		this.crabCollectionFormFacade = crabCollectionFormFacade;
		this.shellSizeFormFacade = shellSizeFormFacade;
		this.shellCollectionFormFacade = shellCollectionFormFacade;
		this.controlFormFacade = controlFormFacade;
		this.reforestationFormFacade = reforestationFormFacade;
		this.limitsFormFacade = limitsFormFacade;
		this.economicIndicatorsFormFacade = economicIndicatorsFormFacade;
		this.data = new MatricesDataUtil(urlBase);
	}

	public File get(Integer orgId, Long startLong, Long endLong){
		matrices = new ArrayList<>();
		addMatrizGeneral(orgId, startLong, endLong);
		addMatricesPlanManejo(orgId, startLong, endLong);
		addMatrizReglamento(orgId, startLong, endLong);
		addMatrizAreaBajoCustodias(orgId, startLong, endLong);
		addMatrizUsoSostenible(orgId, startLong, endLong);
		addMatrizCustodiaDelManglar(orgId, startLong, endLong);
		addMatrizMonitoreoManglar(orgId, startLong, endLong);
		String cadenaHtml = tableUtil.getTablesJoin(matrices);
		String nombreReporte = "form";
		File file = new PdfUtil().generarFichero(cadenaHtml, nombreReporte, true);
		return file;
	}

	private void addMatrizGeneral(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		OfficialDocsForm officialDocsForm = officialDocsFormFacade.getLastByOrg(orgId);
		ManagementPlanForm managementPlanForm = managementPlanFormFacade.getLastByOrg(orgId);
		LimitsForm limitsForm = limitsFormFacade.getLast();
		EconomicIndicatorsForm economicIndicatorsForm = economicIndicatorsFormFacade.getLastByOrg(orgId, start, end);

		String[][] titles = {
			{"Matriz E" + getNMatriz() + ": Información General", ""}
		};

		String[][] values = {
			{"Periodo del informe", data.getPeriod(start, end)},
			{"Fecha de entrega", data.getDate()},
			{"Nombre de la organización", officialDocsForm.getOrganizationName()},
			{"Datos de contacto del presidente", data.getContactPresident(officialDocsForm)},
			{"Ubicación del área de custodia", officialDocsForm.getOrganizationLocation()},
			{"No. de hectáreas que custodia", officialDocsForm.getCustodyArea().toString()},
			{"Uso del Área", data.getPrincipalActivity(economicIndicatorsForm)},
			{"¿Ha habido cambio de directiva?", data.getDirectorsChange(officialDocsForm, startLong, endLong)},
			{"¿Ha habido ingresos o salida de socios?", data.getPartnersChange(officialDocsForm, startLong, endLong)},
			{"¿Ha habido un cambio de limites?", data.getLimitsChange(limitsForm, orgId, startLong, endLong)},
			{"¿Han hecho cambios a su plan de manejo?", data.getIfChanges(managementPlanForm, historyChangeFacade, start, end)},
			{"¿Ha habido cambios en reglamento interno?", data.getRegulationChange(officialDocsForm, startLong, endLong)},
			{"¿Cuenta con una carta de cooperación, convenio u otro documento de compromiso de asistencia técnica vigente?", data.getAgreements(officialDocsForm, startLong, endLong)},		
		};
		Integer[] widhtColumns = {40, 60};
		Integer[] resaltColumns = {0};
		Integer[] resaltRows = {};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}

	private String getNMatriz() {
		return (matrices.size() + 1) + "";
	}

	private void addMatricesPlanManejo(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		ManagementPlanForm managementPlanForm = managementPlanFormFacade.getLastByOrg(orgId);
		PlanTrackingForm planTrackingForm = planTrackingFormFacade.getLastByOrg(orgId, start, end);
		List<EvidenceForm> evidenceForms = evidenceFormFacade.getByOrgAndDates(orgId, start, end);
		List<PlanInfo> programs = ReportsUtil.getFromManagmentPlanFormPlansInfo(managementPlanForm, "program");
		for (PlanInfo program : programs) {
			if (program.getInfo() == null) continue;
			String programName = program.getInfo();
			List<PlanInfo> activities = ReportsUtil.getActivitiesFromProgram(program);
			String matriz = getMatrizPlanManejo(orgId, start, end, programName, activities, evidenceForms, planTrackingForm);
			matrices.add(matriz);
		}
	}

	private String getMatrizPlanManejo(Integer orgId, Date start, Date end, String programName, List<PlanInfo> activities, List<EvidenceForm> evidenceForms, PlanTrackingForm planTrackingForm){
		
		String[][] titles = {
			{"Matriz E" + getNMatriz() + ": Cumplimiento del Plan de Manejo","","","","",""},
			{"Programa: " + programName,"","","","",""}
		};

		String[][] values = new String[activities.size()+1][6];

		values[0] = new String[]{"Acciones propuestas en el Plan de Manejo", "Indicador propuesto", "Nivel de cumplimiento (%)", "Descripción de las actividades cumplidas", "Medios de verificación o Evidencias", "Observaciones"};

		for (int i= 1; i < activities.size() + 1; i++) {
			PlanInfo planInfo = activities.get(i-1);
			String activity = planInfo.getInfo();
			String indicators =  data.getIndicators(planInfo);
			String percentage = data.getPercentage(activity, planTrackingForm);
			String activityDesc = data.getActivityDesc(activity, evidenceForms);
			String evidences = data.getEvidences(activity, evidenceForms);
			String observations = data.getObservations(activity, evidenceForms);
			values[i] = new String[]{activity, indicators, percentage, activityDesc, evidences, observations};
		}
		Integer[] widhtColumns = {25, 20, 5, 15, 20, 15};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {0};
		return tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
	}

	private void addMatrizReglamento(Integer orgId, Long startLong, Long endLong){
		OfficialDocsForm officialDocsForm = officialDocsFormFacade.getLastByOrg(orgId);

		String[][] titles = {
			{"Matriz E" + getNMatriz() + ": Reglamento interno", ""}
		};

		String[][] values = {
			{"Reglamento interno", data.getUrlReglamento(officialDocsForm)},			
		};
		Integer[] widhtColumns = {40, 60};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}


	private void addMatrizAreaBajoCustodias(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		OrganizationInfoForm organizationInfoForm = organizationInfoFormFacade.getLastByOrg(orgId, start, end);
		OfficialDocsForm officialDocsForm = officialDocsFormFacade.getLastByOrg(orgId);

		String[][] titles = {
			{"Matriz E" + getNMatriz() + ": Cesión/Fracción/ ampliación del área bajo custodia","","","","",""}
		};

		String[][] values = {
			{"Reglas relacionado con cesión, fracción, enajacion, ampliación del área bajo custodia", "Si", "No", "Descripción", "Evidencia (en caso de haber)", "Observaciones"},
			data.getRow(organizationInfoForm, "cumpleReglaNoEnajenar"),
			data.getRow(organizationInfoForm, "cumpleReglaNoAmpliar"),
			data.getRowBuenaVecindad(officialDocsForm),
			data.getRow(organizationInfoForm, "cumpleReglaBuenaVecindad"),
			data.getRow(organizationInfoForm, "pescadoresIndependientes"),
			data.getRow(organizationInfoForm, "pescadoresDeOtrasOrgs"),
			data.getRow(organizationInfoForm, "denuncioPescadoresNoAutorizados"),
		};

		Integer[] widhtColumns = {30, 5, 5, 30, 15, 15};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {0};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}

	private void addMatrizUsoSostenible(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		OrganizationInfoForm organizationInfoForm = organizationInfoFormFacade.getLastByOrg(orgId, start, end);
		List<CrabCollectionForm> crabCollectionForms = crabCollectionFormFacade.getByOrgAndDates(orgId, start, end);
		List<CrabSizeForm> crabSizeForms = crabSizeFormFacade.getByOrgAndDates(orgId, start, end);
		List<ShellCollectionForm> shellCollectionForms = shellCollectionFormFacade.getByOrgAndDates(orgId, start, end);
		List<ShellSizeForm> shellSizeForms = shellSizeFormFacade.getByOrgAndDates(orgId, start, end);
		
		String[][] titles = {
			{"Matriz E" + getNMatriz() + " Uso Sostenible","","","","",""}
		};

		String[][] values = {
			{"Se realizaron acciones encaminados a la pesca extractiva sostenible:", "Si", "No", "Descripción", "Medios de verificación/evidencias", "Observaciones"},
			data.getRowRealizoPesca(crabCollectionForms, shellCollectionForms, orgId, startLong, endLong),
			data.getRowRealizoControl(crabSizeForms, shellSizeForms, orgId, startLong, endLong),
			data.getRow(organizationInfoForm, "todosRecurosBioacuaticos"),
			data.getRow(organizationInfoForm, "pescaFueraDelManglar"),
			data.getRowNConchas(shellCollectionForms, orgId, startLong, endLong),
			data.getRowNCangrejos(crabCollectionForms, orgId, startLong, endLong),

			{"Se realizaron acciones encaminados a la cría, engorde o cultivo de especies bioacuática:", "Si", "No", "Descripción", "Medios de verificación/evidencias", "Observaciones"},
			data.getRow(organizationInfoForm, "actividadesDeCria"),
			data.getRow(organizationInfoForm, "cumpleNormasCria"),
			data.getRow(organizationInfoForm, "devuelveIndividuosDecomisados"),

			{"Se realizaron acciones encaminados al turismo ecológico:", "Si", "No", "Descripción", "Medios de verificación/evidencias", "Observaciones"},
			data.getRow(organizationInfoForm, "actividadesTurismo"),
			data.getRow(organizationInfoForm, "normativaTuristica"),
		};

		Integer[] widhtColumns = {30, 5, 5, 30, 15, 15};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {0, 7, 11};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}

	private void addMatrizCustodiaDelManglar(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		OrganizationInfoForm organizationInfoForm = organizationInfoFormFacade.getLastByOrg(orgId, start, end);
		List<ControlForm> controlForms = controlFormFacade.getByOrgAndDates(orgId, start, end);
		List<ReforestationForm> reforestationForms = reforestationFormFacade.getByOrgAndDates(orgId, start, end);

		String[][] titles = {
			{"Matriz E" + getNMatriz() + " Custodia del Manglar","","","","",""}
		};

		String[][] values = {
			{"Se realizaron acciones encaminados a la Conservación del Manglar:", "Si", "No", "Descripción", "Evidencias", "Observaciones"},
			data.getRowRealizaControlYVigilancia(controlForms, orgId, startLong, endLong),
			data.getRowRealizoReforestacion(reforestationForms, orgId, startLong, endLong),
			data.getRow(organizationInfoForm, "cuentaSocioManglar"),

			{"Se realizaron acciones encaminados a la Educación Ambiental e Investigación:", "Si", "No", "Descripción", "Medios de verificación/evidencias", "Observaciones"},
			data.getRow(organizationInfoForm, "actividadesEducacionAmbiental"),
			data.getRow(organizationInfoForm, "actividadesInvestigacion"),
			data.getRow(organizationInfoForm, "cuentaConPermisos"),
		};
		
		Integer[] widhtColumns = {30, 5, 5, 30, 15, 15};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {0, 4};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}

	private void addMatrizMonitoreoManglar(Integer orgId, Long startLong, Long endLong){
		Date start = new Date(startLong);
		Date end = new Date(endLong);
		OrganizationInfoForm organizationInfoForm = organizationInfoFormFacade.getLastByOrg(orgId, start, end);

		String[][] titles = {
			{"Matriz E" + getNMatriz() + " Monitoreo participativo del estado del manglar","","","","",""}
		};

		String[][] values = {
			{"Monitoreo del estado del Manglar:", "Si", "No", "Descripción", "Evidencias", "Observaciones"},
			data.getRow(organizationInfoForm, "evidencioMuerteArboles"),
			data.getRow(organizationInfoForm, "evidencioMuerteBioacuaticos"),
			data.getRow(organizationInfoForm, "evidencioMuerteFlora"),
			data.getRow(organizationInfoForm, "evidencioMuerteFauna"),
			data.getRow(organizationInfoForm, "cambiosEnAgua"),
			data.getRow(organizationInfoForm, "cambiosSuelo"),
			data.getRow(organizationInfoForm, "cambiosClima"),
			data.getRow(organizationInfoForm, "cambiosTemperatura"),
		};
		Integer[] widhtColumns = {30, 5, 5, 30, 15, 15};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {0};
		String matriz = tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
		matrices.add(matriz);
	}

}
