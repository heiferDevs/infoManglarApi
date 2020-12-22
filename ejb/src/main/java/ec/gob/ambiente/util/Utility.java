package ec.gob.ambiente.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public final class Utility {

	// dateStr 2020-04-12
	public static Date getDate(String dateStr){
		  if (dateStr == null) return new Date();
		  try {
			  SimpleDateFormat isoFormat = new SimpleDateFormat("yyyy-MM-dd");
			  isoFormat.setTimeZone(TimeZone.getTimeZone("America/Guayaquil"));
			  //isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
			  Date result = isoFormat.parse(dateStr);
			  return fixDate(result);
		  } catch (Exception e) {
		  }
		  return new Date();
	}

	// format "yyyy-MM-dd"
	// timestamp 1586408400000
	public static String getDate(long timestamp, String format){
		Date date = fixDate(new Date(timestamp));
		return new SimpleDateFormat(format).format(date);
	}

	private static Date fixDate(Date date){
		  Calendar cal = Calendar.getInstance();
		  cal.setTime(date);
		  cal.add(Calendar.HOUR_OF_DAY, 5); // fix server timezone fail remove on prod
		  return cal.getTime();
	}

}
