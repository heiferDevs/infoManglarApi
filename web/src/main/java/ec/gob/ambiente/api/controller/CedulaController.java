package ec.gob.ambiente.api.controller;

import ec.gob.ambiente.suia.controller.RegistroCivilController;
import ec.gob.ambiente.suia.utils.JsfUtil;
import ec.gob.registrocivil.consultacedula.Cedula;

public class CedulaController {

	public Cedula getCedula(String pin){
		if (!JsfUtil.validarCedulaORUC(pin)){
			return getCedulaError();
		  }
		//   TODO: Registro Civil develop missing
    	RegistroCivilController regCivil=new RegistroCivilController();
    	Cedula cedula = regCivil.consultarCedula(pin);
    	if (cedula == null) return getCedulaError();
    	return cedula;
	}

	private Cedula getCedulaError(){
		Cedula cedulaError = new Cedula();
		cedulaError.setError("ERROR Cédula no válida");
		return cedulaError;
	}

}
