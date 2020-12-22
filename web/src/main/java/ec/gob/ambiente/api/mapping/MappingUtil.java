package ec.gob.ambiente.api.mapping;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ec.gob.ambiente.api.resource.FileResource;
import ec.gob.ambiente.infomanglar.forms.model.FileForm;
import ec.gob.ambiente.infomanglar.forms.model.LimitsForm;
import ec.gob.ambiente.infomanglar.forms.model.MappingForm;
import ec.gob.ambiente.infomanglar.forms.services.LimitsFormFacade;
import ec.gob.ambiente.infomanglar.forms.services.MappingFormFacade;
import ec.gob.ambiente.infomanglar.model.OrgMapping;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class MappingUtil {

	private MappingFormFacade mappingFormFacade;
	private LimitsFormFacade limitsFormFacade;
	private JSONParser parser = new JSONParser();
	
	public MappingUtil(MappingFormFacade mappingFormFacade, LimitsFormFacade limitsFormFacade) {
		super();
		this.mappingFormFacade = mappingFormFacade;
		this.limitsFormFacade = limitsFormFacade;
	}

	public List<String> getSectors(Integer orgId){
		MappingForm mappingForm = getLastMappingForm();
		List<String> results = new ArrayList<>();
		for(OrgMapping orgMapping : mappingForm.getOrgsMapping()){
			if (orgMapping.getOrganizationId().equals(orgId)){
				for (FileForm fileForm : orgMapping.getFileForms()){
					results.addAll(getSectorsFromFileForm(fileForm));					
				}
			}
		}
		return results;
	}

	private List<String> getSectorsFromFileForm(FileForm fileForm){
		String pathFile = FileResource.DIR_FILES + File.separator + fileForm.getName();
		File file = new File(pathFile);
		if (!file.exists()) {
			System.out.println("ERROR FILE MAPPING NOT EXIST " + pathFile);
	        return new ArrayList<String>();
		}
		List<String> results = new ArrayList<>();
		try {
			JSONObject geojson = (JSONObject) parser.parse(new FileReader(pathFile));
		    JSONArray features = (JSONArray) geojson.get("features");
		    for (Object f : features) {
		      JSONObject feature = (JSONObject) f;
		      JSONObject properties = (JSONObject) feature.get("properties");
		      if (properties != null) {
			      String sector = (String) properties.get("name");
			      results.add(sector);
		      }
		    }
		} catch (Exception e) {
			System.out.println("ERROR FILE MAPPING NOT SECTOR INCLUDED " + pathFile);
		}
		return results;
	}

	private MappingForm getLastMappingForm(){
		List<MappingForm> list = mappingFormFacade.findAll();
		if (list.size() > 0) {
			return list.get(list.size()-1);
		}
		return new MappingForm();
	}

	private LimitsForm getLastLimitsForm(){
		List<LimitsForm> list = limitsFormFacade.findAll();
		if (list.size() > 0) {
			return list.get(list.size()-1);
		}
		return new LimitsForm();
	}

	public Map<String, JSONObject> getGeometries(Integer orgId){
		Map<String, JSONObject> results = new HashMap<>();
		// MAPPING FORM
		MappingForm mappingForm = getLastMappingForm();
		for(OrgMapping orgMapping : mappingForm.getOrgsMapping()){
			if (orgMapping.getOrganizationId().equals(orgId)){
				for (FileForm fileForm : orgMapping.getFileForms()){
					Map<String, JSONObject> geometries = getGeometriesFromFile(fileForm);
					if (geometries != null) {
						results.putAll(geometries);
					}
				}
			}
		}
		return results;
	}

	public Map<String, JSONObject> getGeometriesLimits(Integer orgId){
		Map<String, JSONObject> results = new HashMap<>();
		// LIMITS FORM
		LimitsForm limitsForm = getLastLimitsForm();
		for(OrgMapping orgMapping : limitsForm.getOrgsMapping()){
			if (orgMapping.getOrganizationId().equals(orgId)){
				for (FileForm fileForm : orgMapping.getFileForms()){
					Map<String, JSONObject> geometries = getGeometriesFromFile(fileForm);
					if (geometries != null) {
						results.putAll(geometries);
					}
				}
			}
		}

		return results;
	}

	private Map<String, JSONObject> getGeometriesFromFile(FileForm fileForm){
		String pathFile = FileResource.DIR_FILES + File.separator + fileForm.getName();
		File file = new File(pathFile);
		if (!file.exists()) {
			System.out.println("ERROR FILE MAPPING NOT EXIST " + pathFile);
	        return null;
		}
		Map<String, JSONObject> results = new HashMap<>();
		try {
			JSONObject geojson = (JSONObject) parser.parse(new FileReader(pathFile));
		    JSONArray features = (JSONArray) geojson.get("features");
		    for (Object f : features) {
		      JSONObject feature = (JSONObject) f;
		      JSONObject properties = (JSONObject) feature.get("properties");
		      JSONObject geometry = (JSONObject) feature.get("geometry");
		      if (properties != null & geometry != null) {
			      String sector = (String) properties.get("name");
			      results.put(sector, geometry);
			  }
		    }
		} catch (Exception e) {
			System.out.println("ERROR FILE MAPPING NOT SECTOR INCLUDED " + pathFile);
		}
		return results;
	}

	public static Integer tryParse(String value, Integer defaultVal) {
	    try {
	        return Integer.parseInt(value);
	    } catch (NumberFormatException e) {
	        return defaultVal;
	    }
	}

}
