package ec.gob.ambiente.api.reports;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;

public class PdfReportsUtil {

	private TableUtil tableUtil = new TableUtil();
	private KeysUtil keysUtil = new KeysUtil();
	private String[] ignoreKeys = {"formType", "userId", "organizationManglarId", "officialDocsFormId", "controlFormId", "crabSizeFormId", "deforestationFormId", "descProjectsFormId", "economicIndicatorsFormId", "infoVedaFormId", "investmentsOrgsFormId", "planTrackingFormId", "pricesFormId", "reforestationFormId", "shellSizeFormId", "shellCollectionFormId", "crabCollectionFormId", "evidenceFormId", "managementPlanFormId", "socialIndicatorsFormId", "technicalReportFormId", "fishingEffortFormId"};

	public String getHtml(List<?> forms, String title){
		if (forms.size() == 0) {
			return "NO_DATA";
		}
	    StringBuilder sb = new StringBuilder();
		ObjectMapper m = new ObjectMapper();
		sb.append(getTitle(title));
		for (Object form : forms) {
			sb.append(getHtmlValues(m, form));
			sb.append(tableUtil.TABLE_SEPARATOR_MIN);
		}
		return sb.toString();
	}

	public String getHtml(Object form, String title){
	    StringBuilder sb = new StringBuilder();
		ObjectMapper m = new ObjectMapper();
		sb.append(getTitle(title));
		sb.append(getHtmlValues(m, form));
		sb.append(tableUtil.TABLE_SEPARATOR_MIN);
		return sb.toString();
	}

	public String getHtmlValues(ObjectMapper m, Object form){
		List<Info> info = getInfo(m, form);

		String[][] titles = {
			{"Formulario",""}
		};

		String[][] values = new String[info.size()][2];

		for (int i= 0; i < info.size(); i++) {
			values[i] = new String[]{info.get(i).getKey(), info.get(i).getValue()};
		}
		Integer[] widhtColumns = {40, 60};
		Integer[] resaltColumns = {};
		Integer[] resaltRows = {};
		return tableUtil.getTable(titles, values, widhtColumns, resaltColumns, resaltRows);
	}

	@SuppressWarnings("unchecked")
	private List<Info> getInfo(ObjectMapper m, Object form){
		Map<String,Object> props = m.convertValue(form, Map.class);
		List<Info> info = new ArrayList<>();
		// General values
		for(Map.Entry<String, Object> entry : props.entrySet()) {
		    addGeneralValues(entry, info);
		}
		// Last values
		for(Map.Entry<String, Object> entry : props.entrySet()) {
		    addSizesCrabsShells(entry, info);
		    addEvidences(entry, info);
		}
		return info;
	}

	private void addGeneralValues(Map.Entry<String, Object> entry, List<Info> info){
	    if(entry.getValue() instanceof String || entry.getValue() instanceof Integer || entry.getValue() instanceof Double){
	    	String key = entry.getKey();
	    	String value = entry.getValue().toString();
	    	boolean pass = Arrays.asList(ignoreKeys).indexOf(key) < 0;
	    	if (pass) {
		    	info.add(new Info(keysUtil.get(key), value));		    		
	    	}
	    }
	}

	private void addSizesCrabsShells(Map.Entry<String, Object> entry, List<Info> info){
		if(entry.getValue() instanceof List && "sizeForms".equals(entry.getKey())){
	    	String key = entry.getKey();
	    	List<Map<String,Object>> sizeForms = cast(entry.getValue());
	    	for (int i = 0; i < sizeForms.size(); i++) {
	    		Map<String,Object> sizeForm = sizeForms.get(i);
	    		System.out.println("width" + sizeForm.get("width"));
	    		String sex = sizeForm.get("sex") == null ? "" : " sexo: " + sizeForm.get("sex");
	    		String value = "ancho: " + sizeForm.get("width") + " largo: " + sizeForm.get("length") + sex;
		    	info.add(new Info(keysUtil.get(key) + " " + (i+1), value));		    				    		
	    	}
	    }
	}

	private void addEvidences(Map.Entry<String, Object> entry, List<Info> info){
		if(entry.getValue() instanceof List && "evidenceActivities".equals(entry.getKey())){
	    	String key = entry.getKey();
	    	List<Map<String,Object>> sizeForms = cast(entry.getValue());
	    	for (int i = 0; i < sizeForms.size(); i++) {
	    		Map<String,Object> evidence = sizeForms.get(i);
	    		String evidenceType = evidence.get("evidenceType") == null ? "" : evidence.get("evidenceType").toString();
	    		String file = evidence.get("fileForms") == null ? "" : getFile(evidence.get("fileForms"));
	    		String value = evidenceType + " " + file;
		    	info.add(new Info(keysUtil.get(key) + " " + (i+1), value));
	    	}
	    }
	}

	private String getFile(Object filesObj){
		List<String> files = new ArrayList<>();
    	List<Map<String,Object>> list = cast(filesObj);
		for (Map<String,Object> f : list) {
    		String name = f.get("name") == null ? "" : f.get("name").toString();
    		String type = f.get("type") == null ? "" : f.get("type").toString();
    		String url = f.get("url") == null ? "" : f.get("url").toString();
    		String link = "<a href=\"" + url + "\">" + name + "</a>";
    		if ("photo".equals(type)) {
    			link = "<br/><img style='height: 200px' src='" + url + "'></img>";
    		}
    		files.add(link);
    	}
		return AbstractFacade.join(files, "<br/>");
	}

	@SuppressWarnings("unchecked")
	public static <T extends List<?>> T cast(Object obj) {
	    return (T) obj;
	}
	
	private String getTitle(String title){
		return "<h1 style='text-align: center; padding: 40px; font-weight: bold;'>Formularios " + title + "</h1>";
	}

}

class Info {
	@Getter
	@Setter
	String key, value;
	public Info(String key, String value) {
		super();
		this.key = key;
		this.value = value;
	}
}
