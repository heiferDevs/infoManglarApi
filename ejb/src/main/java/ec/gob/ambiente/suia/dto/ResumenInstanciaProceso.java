package ec.gob.ambiente.suia.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import org.jbpm.process.audit.ProcessInstanceLog;

import ec.gob.ambiente.enlisy.model.User;

@Data
public class ResumenInstanciaProceso implements Serializable {

	private static final long serialVersionUID = -5301520148349025811L;

	private Long processInstanceId;

	private String tramite;

	private String usuarioAsociado;

	private String processId;
	
	private Integer estado;

	private Date inicio;

	private Date fin;

	private boolean tramiteResolved;
	
	private boolean variable;

	public ResumenInstanciaProceso(ProcessInstanceLog processInstanceLog, String tramite, String usuarioAsociado) {
		this.processInstanceId = processInstanceLog.getProcessInstanceId();
		this.processId = processInstanceLog.getProcessId();
		this.tramite = tramite;
		this.usuarioAsociado = usuarioAsociado;
		this.estado = processInstanceLog.getStatus();
		this.inicio = processInstanceLog.getStart();
		this.fin = processInstanceLog.getEnd();
	}

	public ResumenInstanciaProceso(ProcessInstanceLog processInstanceLog, String tramite) {
		this(processInstanceLog, tramite, null);
	}
	
	public ResumenInstanciaProceso(ProcessInstanceLog processInstanceLog, String tramite, User usuario, Boolean variable) {
		this(processInstanceLog, tramite, null);
		this.variable = variable;
	}

	public ResumenInstanciaProceso() {

	}

	public String getEstadoString() {
		if(variable && estado == 2){
			return "Abortado por no adjuntar requisitos";
		}else{
			if (estado == null)
				return "";
			if (estado == 0)
				return "No iniciado";
			if (estado == 1)
				return "En curso";
			if (estado == 2)
				return "Completado";
			if (estado == 3)
				return "Abortado";
		}
		return estado.toString();
	}
}
