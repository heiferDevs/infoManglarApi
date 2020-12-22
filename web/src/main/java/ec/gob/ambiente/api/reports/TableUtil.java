package ec.gob.ambiente.api.reports;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import ec.gob.ambiente.enlisy.dao.AbstractFacade;

public class TableUtil {

	public final String TABLE_SEPARATOR = "<div style='height: 100px;'></div>";
	public final String TABLE_SEPARATOR_MIN = "<div style='height: 50px;'></div>";

	public String getTable(String[][] titles, String[][] values, Integer[] widhtColumns, Integer[] resaltColumns, Integer[] resaltRows){
		StringBuilder mensaje = new StringBuilder();
		String style = "border-collapse: collapse; width: 100%;";
		mensaje.append("<table style='" + style + "'>");
		mensaje.append(getRowTitles(titles, widhtColumns));
		for (int i = 0; i < values.length; i++) {
			String[] row = values[i];
			mensaje.append(getRowValue(row, resaltColumns, resaltRows, i));
		}
		mensaje.append("</table>");
		return mensaje.toString();
	}

	private String getRowTitles(String[][] values, Integer[] widhtColumns){
		StringBuilder mensaje = new StringBuilder();
		String style = "font-weight: bold; background-color: #dddddd; font-size: 12px;" + getStyleBase();
		for ( String[] valueRow : values ) {
			mensaje.append("<tr>");
			for ( int i = 0; i < valueRow.length; i++ ) {
				String styleToUse = "width: " + widhtColumns[i] + "%;" + style;
				mensaje.append("<th style='" + styleToUse + "'>" + valueRow[i] + "</th>");
			}
			mensaje.append("</tr>");
		}
		return mensaje.toString();
	}

	private String getRowValue(String[] values, Integer[] resaltColumns, Integer[] resaltRows, int indexRow){
		StringBuilder mensaje = new StringBuilder();
		mensaje.append("<tr>");
		String styleNoTitle = "font-size: 12px;" + getStyleBase();
		String styleTitle = "font-weight: bold; font-size: 12px; background-color: #f0f0f0;" + getStyleBase();
		for ( int i = 0; i < values.length; i++ ) {
			boolean resaltColumn = Arrays.asList(resaltColumns).indexOf(i) >= 0;
			boolean resaltRow = Arrays.asList(resaltRows).indexOf(indexRow) >= 0;
			String style = (resaltColumn || resaltRow) ? styleTitle : styleNoTitle;
			mensaje.append("<td style='" + style + "'>" + values[i] + "</td>");
		}
		mensaje.append("</tr>");
		return mensaje.toString();
	}

	private String getStyleBase(){
		return "border: 1px solid #dddddd; text-align: left; padding: 12px 8px 10px 8px;";
	}

	public String getTablesJoin(String[] tables){
		List<String> list = Arrays.asList(tables);
		return getTablesJoin(list);		
	}

	public String getTablesJoin(List<String> list){
		return AbstractFacade.join(list, TABLE_SEPARATOR);		
	}

}
