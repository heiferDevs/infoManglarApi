package ec.gob.ambiente.api.mapping;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ec.gob.ambiente.api.controller.AccessController;
import ec.gob.ambiente.api.reports.ReportsUtil;
import ec.gob.ambiente.api.util.ValidateUtil;
import ec.gob.ambiente.enlisy.model.Role;
import ec.gob.ambiente.enlisy.model.RolesUser;
import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.enlisy.services.RolesUserFacade;
import ec.gob.ambiente.enlisy.services.UserFacade;
import ec.gob.ambiente.infomanglar.forms.model.CrabCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.CrabSizeForm;
import ec.gob.ambiente.infomanglar.forms.model.InfoVedaForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellCollectionForm;
import ec.gob.ambiente.infomanglar.forms.model.ShellSizeForm;
import ec.gob.ambiente.infomanglar.forms.services.CrabCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.CrabSizeFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.InfoVedaFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.MappingFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellCollectionFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.ShellSizeFormFacade;
import ec.gob.ambiente.infomanglar.model.OrganizationManglar;
import ec.gob.ambiente.infomanglar.services.OrganizationManglarFacade;
import lombok.Getter;
import lombok.Setter;

public class FilterMappingUtil {

	private static String ALL_FILTER = "all";

	private UserFacade userFacade;
	private OrganizationManglarFacade organizationManglarFacade;

	private RolesUserFacade rolesUserFacade;

	private ShellSizeFormFacade shellSizeFormFacade;
	private ShellCollectionFormFacade shellCollectionFormFacade;
	private CrabSizeFormFacade crabSizeFormFacade;
	private CrabCollectionFormFacade crabCollectionFormFacade;
	private InfoVedaFormFacade infoVedaFormFacade;
	private MappingUtil mappingUtil;

	public FilterMappingUtil(UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade,
			RolesUserFacade rolesUserFacade,
			ShellSizeFormFacade shellSizeFormFacade,
			ShellCollectionFormFacade shellCollectionFormFacade,
			CrabSizeFormFacade crabSizeFormFacade,
			CrabCollectionFormFacade crabCollectionFormFacade,
			InfoVedaFormFacade infoVedaFormFacade,
			MappingFormFacade mappingFormFacade,
			LimitsFormFacade limitsFormFacade) {
		super();
		this.userFacade = userFacade;
		this.organizationManglarFacade = organizationManglarFacade;
		this.rolesUserFacade = rolesUserFacade;
		this.shellSizeFormFacade = shellSizeFormFacade;
		this.shellCollectionFormFacade = shellCollectionFormFacade;
		this.crabSizeFormFacade = crabSizeFormFacade;
		this.crabCollectionFormFacade = crabCollectionFormFacade;
		this.infoVedaFormFacade = infoVedaFormFacade;
		this.mappingUtil = new MappingUtil(mappingFormFacade, limitsFormFacade);
	}

	public JSONObject get(String formType, String userType, String userId, String orgId, String startTs, String endTs) {
		List<FilterData> reports = new ArrayList<>();
		
		// FORMS
		List<ShellSizeForm> shellSizeForms = shellSizeFormFacade.findBy("ShellSizeForm", "shell-size-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<ShellCollectionForm> shellCollectionForms = shellCollectionFormFacade.findBy("ShellCollectionForm", "shell-collection-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<CrabSizeForm> crabSizeForms = crabSizeFormFacade.findBy("CrabSizeForm", "crab-size-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<CrabCollectionForm> crabCollectionForms = crabCollectionFormFacade.findBy("CrabCollectionForm", "crab-collection-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		List<InfoVedaForm> infoVedaForms = infoVedaFormFacade.findBy("InfoVedaForm", "info-veda-form", ALL_FILTER, formType, userId, orgId, startTs, endTs);
		
		// ADD REPORTS
		for (ShellSizeForm form : shellSizeForms) reports.add(new FilterData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (ShellCollectionForm form : shellCollectionForms) reports.add(new FilterData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (CrabSizeForm form : crabSizeForms) reports.add(new FilterData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (CrabCollectionForm form : crabCollectionForms) reports.add(new FilterData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));
		for (InfoVedaForm form : infoVedaForms) reports.add(new FilterData(form, getUserType(form.getUserId()), userFacade, organizationManglarFacade));

		List<FilterData> reportsFilterByUser = filterUserType(reports, userType);
		List<FilterData> filtered = sortByDate(reportsFilterByUser);

		// FEATURES FORMS
		Map<String, JSONObject> geometries = mappingUtil.getGeometries(MappingUtil.tryParse(orgId, -1));
		List<Feature> features = getFeatures(filtered, geometries);

		// FEATURES LIMITS
		Map<String, JSONObject> geometriesLimits = mappingUtil.getGeometriesLimits(MappingUtil.tryParse(orgId, -1));
		List<Feature> featuresLimits = getFeaturesLimits(geometriesLimits);
		
		List<Feature> allFeatures = new ArrayList<>(features);
		if (features.size() > 0) {
			allFeatures.addAll(featuresLimits);
		}

		return getJsonObj(allFeatures);
	}

	@SuppressWarnings("unchecked")
	private JSONObject getJsonObj(List<Feature> features) {
		JSONObject json = new JSONObject();
		json.put("type", "FeatureCollection");
		json.put("name", "ResultadoQuery");
		JSONArray jsonFeatures = new JSONArray();
		for(Feature feature : features) {
			JSONObject f = new JSONObject();
			f.put("type", "Feature");
				JSONObject properties = new JSONObject();
				properties.put("name", feature.getName());
				properties.put("count", feature.getCount());
				properties.put("total", feature.getTotal());
				properties.put("max", feature.getMax());
				properties.put("detail", feature.getDetail());
			f.put("properties", properties);
			f.put("geometry", feature.geometry);
			jsonFeatures.add(f);
		}
		json.put("features", jsonFeatures);
		return json;
	}

	private List<Feature> getFeatures(List<FilterData> filtered, Map<String, JSONObject> geometries) {
		 Map<String, Feature> map = new HashMap<>();
		 int countTotal = 0;
		 int countMax = 0;
		 for (FilterData filterData : filtered) {
			 String sectorName = filterData.getSector();
			 JSONObject geometry = geometries.get(sectorName);
			 if (geometry == null) {
				 System.out.println("Warning: geometry null for " + sectorName + " can be caused for old config areas");
				 continue;
			 }
			 if (map.containsKey(sectorName)) {
				 updateFeature(map.get(sectorName), filterData);
			 }
			 else {
				 Feature feature = new Feature(geometry, sectorName, filterData.getCount(),
						 getCountDetail(filterData.getTypeForm(), filterData.getCount()));
				 map.put(sectorName, feature);
			 }
			 countTotal += filterData.getCount();
			 int currentCount = map.get(sectorName).getCount();
			 if (currentCount > countMax) countMax = currentCount;
		 }
		 List<Feature> results = new ArrayList<Feature>(map.values());
		 for (Feature feature : results) {
			 feature.setTotal(countTotal);
			 feature.setMax(countMax);
		 }
		 return results;
	}

	private List<Feature> getFeaturesLimits(Map<String, JSONObject> geometries) {
		 Map<String, Feature> map = new HashMap<>();

		for (Map.Entry<String, JSONObject> entry : geometries.entrySet()) {
		    String sectorName = entry.getKey();
			JSONObject geometry = entry.getValue();
			Feature feature = new Feature(geometry, sectorName, 0, "limite");
			map.put(sectorName, feature);
		}
		 List<Feature> results = new ArrayList<Feature>(map.values());
		 for (Feature feature : results) {
			 feature.setTotal(0);
			 feature.setMax(0);
		 }
		 return results;
	}

	private void updateFeature(Feature feature, FilterData filterData) {
		int newCount = feature.getCount() + filterData.getCount();
		feature.setCount(newCount);
		feature.setDetail(getCountDetail(filterData.getTypeForm(), newCount));
	}

	private List<FilterData> filterUserType(List<FilterData> reports, String userType){
		if (ALL_FILTER.equals(userType)) {
			return reports;
		}
		List<FilterData> results = new ArrayList<>();
		for (FilterData reportData :  reports) {
			if (userType.equals(reportData.getUserType())) {
				results.add(reportData);
			}
		}
		return results;
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

	private List<FilterData> sortByDate(List<FilterData> reports) {
		Collections.sort(reports, new Comparator<FilterData>() {
		    @Override
		    public int compare(FilterData r1, FilterData r2) {
		        return r1.getCreatedAt() > r2.getCreatedAt() ? -1 :
		        	(r1.getCreatedAt() < r2.getCreatedAt()) ? 1 : 0;
		    }
		});
		return reports;
	}

	private String getCountDetail(String typeForm, Integer count) {
		  switch (typeForm) {
			case "crab-size-form":
				return "[" + count + " cangrejos]";
			case "info-veda-form":
				return "[" + count + "]";
			case "shell-size-form":
				return "[" + count + " conchas]";
			case "shell-collection-form":
				return "[" + count + " conchas]";
			case "crab-collection-form":
				return "[" + count + " cangrejos]";
		  }
		  return "[NO_DETAIL]";
	}

}


class FilterData {


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

	  @Getter
	  @Setter
	  private String sector;

	  @Getter
	  @Setter
	  private Integer count;

	public FilterData(CrabSizeForm form, String userType,
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
		this.sector = form.getSectorName();
		this.count = form.getCrabCount() == null ? 0 : form.getCrabCount();
	}

	public FilterData(ShellSizeForm form, String userType,
			UserFacade userFacade,
			OrganizationManglarFacade organizationManglarFacade) {
		super();
		User user = userFacade.getUserById(form.getUserId());
		OrganizationManglar organizationManglar = organizationManglarFacade.findById(form.getOrganizationManglarId());
		this.title = ReportsUtil.getTitle(form.getFormType());
		this.subTitle = "";
		this.idForm = form.getShellSizeFormId();
		this.typeForm = form.getFormType();
		this.createdAt = form.getCreatedAt() == null ? null : form.getCreatedAt().getTime();
		this.extraInfo = "";
		this.userId = form.getUserId();
		this.userName = user.getPeople().getPeopName();;
		this.userType = userType;
		this.organizationId = form.getOrganizationManglarId();
		this.organizationName = organizationManglar.getOrganizationManglarName();
		this.sector = form.getSectorName();
		this.count = form.getShellCount() == null ? 0 : form.getShellCount();
	}

	public FilterData(InfoVedaForm form, String userType,
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
		this.sector = form.getSectorName();
		this.count = 1;
	}

	public FilterData(ShellCollectionForm form, String userType,
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
		this.sector = form.getSectorName();
		this.count = (form.getSimilisShellsCount() == null ? 0 : form.getSimilisShellsCount()) +
				(form.getTuberculosasShellsCount() == null ? 0 : form.getTuberculosasShellsCount());
	}

	public FilterData(CrabCollectionForm form, String userType,
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
		this.sector = form.getSectorName();
		this.count = (form.getCrabCollectedCount() == null ? 0 : form.getCrabCollectedCount());
	}

}

class Feature{

	@Getter
	@Setter
	String name;

	public Feature(JSONObject geometry, String name, Integer count, String detail) {
		this.geometry = geometry;
		this.name = name;
		this.count = count;
		this.detail = detail;
	}

	@Getter
	@Setter
	Integer count;

	@Getter
	@Setter
	Integer total;

	@Getter
	@Setter
	Integer max;

	@Getter
	@Setter
	String detail;

	@Getter
	@Setter
	JSONObject geometry;
	
}
