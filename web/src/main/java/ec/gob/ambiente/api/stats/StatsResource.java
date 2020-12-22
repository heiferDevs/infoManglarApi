package ec.gob.ambiente.api.stats;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import ec.gob.ambiente.api.reports.ReportsUtil;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.ControlForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.DescProjectsForm;
import ec.gob.ambiente.infomanglar.forms.model.EconomicIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.model.InvestmentsOrgsForm;
import ec.gob.ambiente.infomanglar.forms.model.PricesForm;
import ec.gob.ambiente.infomanglar.forms.model.ReforestationForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.SocialIndicatorsForm;
import ec.gob.ambiente.infomanglar.forms.services.ControlFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.DescProjectsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.EconomicIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.InvestmentsOrgsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.PricesFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ReforestationFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.SocialIndicatorsFormFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.model.PriceDaily;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;

@Path("/stats")
public class StatsResource {

	@EJB
	private UserFacade userFacade;

	@EJB
	private DescProjectsFormFacade descProjectsFormFacade;

	@EJB
	private InvestmentsOrgsFormFacade investmentsOrgsFormFacade;

	@EJB
	private EconomicIndicatorsFormFacade economicIndicatorsFormFacade;

	@EJB
	private PricesFormFacade pricesFormFacade;

	@EJB
	private ReforestationFormFacade reforestationFormFacade;

	@EJB
	private SocialIndicatorsFormFacade socialIndicatorsFormFacade;

	@EJB
	private OrganizationManglarFacade organizationManglarFacade;

	@EJB
	private ControlFormFacade controlFormFacade;

	@EJB
	private ShellCollectionFormFacade shellCollectionFormFacade;

	@EJB
	private CrabCollectionFormFacade crabCollectionFormFacade;

	@GET
	@Path("/projects-by-type/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getStatProjectsByType(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<DescProjectsForm> forms = descProjectsFormFacade.findBy("DescProjectsForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		for (DescProjectsForm descProjectsForm : forms) {
			String label = descProjectsForm.getInstitutionType();
			int value = 1;
			if (stats.containsKey(label)) {
				value = stats.get(label) + value;
			}
			stats.put(label, value);
		}
		return new StatsUtil().getResultsPercentage(stats);
	}

	@GET
	@Path("/investments-by-orgs/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getInvestmentsByOrgs(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<InvestmentsOrgsForm> forms = investmentsOrgsFormFacade.findBy("InvestmentsOrgsForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		StatsUtil statsUtil = new StatsUtil();

		String label = "TestOrg";
		double totalInvestment = 0;
		for (InvestmentsOrgsForm investmentsOrgsForm : forms) {
			double totalForm = statsUtil.getTotalInvestment(investmentsOrgsForm);
			totalInvestment += totalForm;
		}
		int value = (int) Math.round(totalInvestment);
		stats.put(label, value);
		return statsUtil.getResults(stats);
	}

	@GET
	@Path("/principal-activity/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getPrincipalActivity(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<EconomicIndicatorsForm> forms = economicIndicatorsFormFacade.findBy("EconomicIndicatorsForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		StatsUtil statsUtil = new StatsUtil();
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (EconomicIndicatorsForm economicIndicatorsForm : forms) {
			String label = economicIndicatorsForm.getPrincipalActivity();
			int count = 1;
			double value = statsUtil.getValueActivity(economicIndicatorsForm.getAverageIncome());
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return new StatsUtil().getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/secondary-activity/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getSecondaryActivity(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<EconomicIndicatorsForm> forms = economicIndicatorsFormFacade.findBy("EconomicIndicatorsForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		StatsUtil statsUtil = new StatsUtil();
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (EconomicIndicatorsForm economicIndicatorsForm : forms) {
			String label = economicIndicatorsForm.getAlternativeActivities();
			int count = 1;
			double value = statsUtil.getValueActivity(economicIndicatorsForm.getAverageIncomeAlternatives());
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return new StatsUtil().getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/bioemprendimiento/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getBioEmprendimiento(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<PricesForm> forms = pricesFormFacade.findBy("PricesForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		for (PricesForm pricesForm : forms) {
			for ( PriceDaily priceDaily : pricesForm.getPriceDailies() ) {
				boolean hasBioemprendimiento = priceDaily.getOtherProducts() != null ||
						priceDaily.getServiceType() != null;
				if ( !hasBioemprendimiento ) continue;
				String label = priceDaily.getOtherProducts() == null ?
						priceDaily.getServiceType() :
						priceDaily.getOtherProducts();
				int value = 1;
				if (stats.containsKey(label)) {
					value = stats.get(label) + value;
				}
				stats.put(label, value);
			}
		}
		return new StatsUtil().getResults(stats);
	}

	@GET
	@Path("/reforestation-area/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getReforestationArea(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<ReforestationForm> forms = reforestationFormFacade.findBy("ReforestationForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		for (ReforestationForm reforestationForm : forms) {
			if (reforestationForm.getReforestedArea() == null) continue;
			String label = reforestationForm.getPlantingState();
			int value = reforestationForm.getReforestedArea().intValue();
			if (stats.containsKey(label)) {
				value = stats.get(label) + value;
			}
			stats.put(label, value);
		}
		return new StatsUtil().getResults(stats);
	}

	@GET
	@Path("/manglar-dependents/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getManglarDependents(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<SocialIndicatorsForm> forms = socialIndicatorsFormFacade.findBy("SocialIndicatorsForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		for (SocialIndicatorsForm socialIndicatorsForm : forms) {
			Integer countManglarDependents = socialIndicatorsForm.getCountManglarDependents();
			if ( countManglarDependents == null || countManglarDependents == 0 ) continue;
			OrganizationManglar organizationManglar = organizationManglarFacade.findById(
					socialIndicatorsForm.getOrganizationManglarId());
			String label = organizationManglar.getOrganizationManglarName();
			int value = countManglarDependents;
			if (stats.containsKey(label)) {
				value = stats.get(label) + value;
			}
			stats.put(label, value);
		}
		return new StatsUtil().getResults(stats);
	}

	@GET
	@Path("/anomalies-by-location/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getAnomaliesByLocation(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		List<ControlForm> forms = controlFormFacade.findBy("ControlForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> stats = new HashMap<>();
		for (ControlForm controlForm : forms) {
			if ( !controlForm.getEventExists() ) continue;
			String label = controlForm.getSector();
			int value = 1;
			if (stats.containsKey(label)) {
				value = stats.get(label) + value;
			}
			stats.put(label, value);
		}
		return new StatsUtil().getResultsPercentage(stats);
	}

	@GET
	@Path("/fishing-effort-shell-by-socio/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortShellBySocio(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<ShellCollectionForm> forms = shellCollectionFormFacade.findBy("ShellCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (ShellCollectionForm shellCollectionForm : forms) {
			double value = statsUtil.getFishingEffortShell(shellCollectionForm);
			if ( value <= 0.0 ) continue;
			User user = userFacade.getUserById(shellCollectionForm.getUserId());
			if ( user == null ) continue;
			String label = user.getPeople().getPeopName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/fishing-effort-shell-by-org/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortShellByOrg(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<ShellCollectionForm> forms = shellCollectionFormFacade.findBy("ShellCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (ShellCollectionForm shellCollectionForm : forms) {
			double value = statsUtil.getFishingEffortShell(shellCollectionForm);
			if ( value <= 0.0 ) continue;
			OrganizationManglar organizationManglar = organizationManglarFacade.findById(
					shellCollectionForm.getOrganizationManglarId());
			if ( organizationManglar == null ) continue;
			String label = organizationManglar.getOrganizationManglarName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/fishing-effort-shell-by-sector/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortShellBySector(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<ShellCollectionForm> forms = shellCollectionFormFacade.findBy("ShellCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (ShellCollectionForm shellCollectionForm : forms) {
			double value = statsUtil.getFishingEffortShell(shellCollectionForm);
			if ( value <= 0.0 ) continue;
			String label = shellCollectionForm.getSectorName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/fishing-effort-crab-by-socio/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortCrabBySocio(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<CrabCollectionForm> forms = crabCollectionFormFacade.findBy("CrabCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (CrabCollectionForm crabCollectionForm : forms) {
			double value = statsUtil.getFishingEffortCrab(crabCollectionForm);
			if ( value <= 0.0 ) continue;
			User user = userFacade.getUserById(crabCollectionForm.getUserId());
			if ( user == null ) continue;
			String label = user.getPeople().getPeopName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/fishing-effort-crab-by-org/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortCrabByOrg(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<CrabCollectionForm> forms = crabCollectionFormFacade.findBy("CrabCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (CrabCollectionForm crabCollectionForm : forms) {
			double value = statsUtil.getFishingEffortCrab(crabCollectionForm);
			if ( value <= 0.0 ) continue;
			OrganizationManglar organizationManglar = organizationManglarFacade.findById(
					crabCollectionForm.getOrganizationManglarId());
			if ( organizationManglar == null ) continue;
			String label = organizationManglar.getOrganizationManglarName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

	@GET
	@Path("/fishing-effort-crab-by-sector/{user-id}/{org-id}/{start}/{end}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<StatData> getFishingEffortCrabBySector(@PathParam("user-id") String userId,
			@PathParam("org-id") String orgId,
			@PathParam("start") String startTs,
			@PathParam("end") String endTs) {
		StatsUtil statsUtil = new StatsUtil();
		List<CrabCollectionForm> forms = crabCollectionFormFacade.findBy("CrabCollectionForm", ReportsUtil.ALL_FILTER, userId, orgId, startTs, endTs);
		Map<String, Integer> statsCount = new HashMap<>();
		Map<String, Double> statsValue = new HashMap<>();
		for (CrabCollectionForm crabCollectionForm : forms) {
			double value = statsUtil.getFishingEffortCrab(crabCollectionForm);
			if ( value <= 0.0 ) continue;
			String label = crabCollectionForm.getSectorName();
			int count = 1;
			if (statsCount.containsKey(label)) {
				count = statsCount.get(label) + count;
				value = statsValue.get(label) + value;
			}
			statsCount.put(label, count);
			statsValue.put(label, value);
		}
		return statsUtil.getResultsAverage(statsCount, statsValue);
	}

}
