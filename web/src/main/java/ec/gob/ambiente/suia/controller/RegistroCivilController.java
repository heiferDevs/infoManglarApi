package ec.gob.ambiente.suia.controller;

import ec.gob.registrocivil.consultacedula.Cedula;

public class RegistroCivilController {

    public Cedula consultarCedula(String pin) {
        System.out.println("Probando que funca ***********");
        Cedula cedula= new Cedula();
        cedula.setCedula(pin);
        cedula.setNombre(" ");
        cedula.setError("NO ERROR");
        // System.out.println(cedula.cedula);
        return cedula;
    }
}