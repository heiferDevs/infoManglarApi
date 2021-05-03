package ec.gob.ambiente.api.model;

// import java.lang.reflect.Constructor;

import lombok.Data;
// import lombok.Getter;
// import lombok.Setter;


@Data
public class Cedula {

	private String cedula;

	private String nombre;

	private String estadoCivil;

	private String nacionalidad;

	private String calleDomicilio;

	private String domicilio;

	private String genero;

	private String error;

	public Cedula(String pin) {
		this.cedula = pin;
	}

	public String getCedula() {
		return this.cedula;
	}

	public void setCedula(String cedula) {
		this.cedula = cedula;
	}

	public String getNombre() {
		return this.nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getError() {
		return this.error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
}
