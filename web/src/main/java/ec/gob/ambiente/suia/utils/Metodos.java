package ec.gob.ambiente.suia.utils;

import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;



public class Metodos {
	
	public Metodos() {
		// TODO Auto-generated constructor stub
	}
	
	//Obtener la fecha actual en java.util.Date
	public static Date nowDate(){
		
		// 1) create a java calendar instance
		java.util.Calendar calendar = Calendar.getInstance();
		
		// 2) get a java.util.Date from the calendar instance.
		//		    this date will represent the current instant, or "now".
		java.util.Date now = calendar.getTime();

		return now;
	} 
	
	//Obtener la fecha actual en java.sql.Timestamp
	public static Timestamp nowTimespan(){		
		java.util.Date now = nowDate();
		
		// a java current time (now) instance
		java.sql.Timestamp currentTimestamp = new java.sql.Timestamp(now.getTime());		
		return currentTimestamp;		
	} 
}