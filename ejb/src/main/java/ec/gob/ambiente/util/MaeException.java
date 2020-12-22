package ec.gob.ambiente.util;

import java.io.Serializable;

public class MaeException extends RuntimeException implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3414317561240503574L; 
	
	/**
	 * Error técnico, descripción del error.
	 */
	private String errorTecnico;

	public String getErrorTecnico() {
		return errorTecnico;
	}

	public void setErrorTecnico(String errorTecnico) {
		this.errorTecnico = errorTecnico;
	}

	public MaeException() {
		
	}

	public MaeException(String arg0) {
		super(arg0);
		
	}

	public MaeException(Throwable arg0) {
		super(arg0);
		
	}

	public MaeException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		
	}

	public MaeException(String msg, String errorTecnico1){
		super(msg);
		StackTraceElement ste[] = this.getStackTrace();
		System.out.println(ste[1].getClassName());
		System.out.println(ste[1].getMethodName());
		this.errorTecnico = ste[1].getClassName() +"::" + ste[1].getMethodName();
		this.errorTecnico = errorTecnico + "\n" +   errorTecnico1;
	}
		
}
