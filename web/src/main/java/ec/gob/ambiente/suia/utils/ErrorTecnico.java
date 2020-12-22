package ec.gob.ambiente.suia.utils;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.bean.ManagedBean;

/**
 * Encapsula error general técnico, dentro de la aplicación para ser mostrada en la vista.
 * @author miguelbaldeon
 *
 */
//@Named(value = "errorTecnico")

@ManagedBean(name="errorTecnico")
@RequestScoped
public class ErrorTecnico implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2579574277229723013L;
	private String error;
	private boolean existeError = false;
	
	
	public void setExisteError(boolean existeError) {
		this.existeError = existeError;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Boolean getExisteError() {
		return existeError;
	}
	
	public boolean getExisteError1(){
		return existeError;
	}
}