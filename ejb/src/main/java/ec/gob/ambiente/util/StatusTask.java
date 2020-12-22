package ec.gob.ambiente.util;

public enum StatusTask {
	
	Reserved, Completed; 

	public static String getStatusName(String status) {
		if (Reserved != null && Reserved.equals(Reserved)){
			return "Reservado";
		} else if (Completed != null && Completed.equals(Completed)){
			return "Completado";
		}
		return null;
	}
}