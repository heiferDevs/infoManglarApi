package ec.gob.ambiente.suia.dto;

import ec.gob.ambiente.util.Constant;


public enum EstadoTarea {

	Completed;

	public static String getNombreEstado(String estado) {
		if (estado.equals(EstadoTarea.Completed.name())) {
			return Constant.ESTADO_TAREA_COMPLETADA;
		} else
			return Constant.ESTADO_TAREA_INICIADA;
	}
}
