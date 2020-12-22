package ec.gob.ambiente.api.reports;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CsvUtil {

	public String getCsvAll(List<ReportData> reports){
	    StringBuilder sb = new StringBuilder();
	    sb.append("\"tableForm\"");sb.append(',');
	    sb.append("\"idForm\"");sb.append(',');
	    sb.append("\"titleForm\"");sb.append(',');
	    sb.append("\"userId\"");sb.append(',');
	    sb.append("\"userName\"");sb.append(',');
	    sb.append("\"userType\"");sb.append(',');
	    sb.append("\"organizationId\"");sb.append(',');
	    sb.append("\"organizationName\"");sb.append(',');
	    sb.append("\"info\"");sb.append(',');
	    sb.append("\"extraInfo\"");sb.append(',');
	    sb.append("\"createdAt\"");sb.append(',');
	    sb.append("\"createdAtTs\"");
	    
	    sb.append('\n');
		for (ReportData report : reports) {
			String created = new SimpleDateFormat("yyyy-MM-dd HH:ss").format(report.getCreatedAt());
		    sb.append("\"" + report.getTypeForm() + "\"");sb.append(',');
		    sb.append("\""+ report.getIdForm() + "\"");sb.append(',');
		    sb.append("\"" + report.getTitle() + "\"");sb.append(',');
		    sb.append("\"" + report.getUserId() + "\"");sb.append(',');
		    sb.append("\"" + report.getUserName() + "\"");sb.append(',');
		    sb.append("\"" + report.getUserType() + "\"");sb.append(',');
		    sb.append("\"" + report.getOrganizationId() + "\"");sb.append(',');
		    sb.append("\"" + report.getOrganizationName() + "\"");sb.append(',');
		    sb.append("\"" + report.getSubTitle() + "\"");sb.append(',');
		    sb.append("\"" + report.getExtraInfo() + "\"");sb.append(',');		
		    sb.append("\"" + created + "\"");sb.append(',');
		    sb.append("\"" + report.getCreatedAt() + "\"");
		    sb.append('\n');
		}
		return sb.toString();
	}

	public String getCsv(List<?> forms){
		if (forms.size() == 0) {
			return "NO_DATA";
		}
	    StringBuilder sb = new StringBuilder();
		ObjectMapper m = new ObjectMapper();
		sb.append(getCsvValues(m, forms.get(0), true));
		for (Object form : forms) {
			sb.append(getCsvValues(m, form, false));
		}
		return sb.toString();
	}

	@SuppressWarnings("unchecked")
	public String getCsvValues(ObjectMapper m, Object form, boolean isTitle){
		StringBuilder values = new StringBuilder();
		Map<String,Object> props = m.convertValue(form, Map.class);
		for(Map.Entry<String, Object> entry : props.entrySet()) {
		    if(entry.getValue() instanceof String || entry.getValue() instanceof Integer || entry.getValue() instanceof Double){
		    	System.out.println(entry.getKey() + "-->" + entry.getValue());
		        if (isTitle){
		        	values.append("\"" + entry.getKey() + "\"");values.append(',');		        	
		        } else {
		        	values.append("\"" + entry.getValue() + "\"");values.append(',');		        	
			    }
		    }
		}
		values.deleteCharAt(values.length()-1);
		values.append('\n');
		return values.toString();
	}

}
