package ec.gob.ambiente.jbpm.service;

import java.util.List;
import java.util.Map;

import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.process.audit.VariableInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.TaskSummary;

public class ProcessBeanFacade {

	public long startProcess(String nombreProceso,
			Map<String, Object> parametros, String deploymentId,
			String userName, String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setProcessVariables(long processInstanceID,
			Map<String, Object> params, String externalId, String userName,
			String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getProcessVariables(long processInstanceID,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstance getProcessInstance(long processInstanceID,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public ProcessInstanceLog getProcessInstanceLog(long processInstanceID,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTaskSumaryByProcessId(Long processInstanceID,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<TaskSummary> getTaskSumaryByProcessIdWithoutCompletedStatus(
			Long processInstanceID, String deploymentId, String userName,
			String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public void abortProcess(long processInstanceId, String deploymentId,
			String userName, String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		
	}

	public List<VariableInstanceLog> getVariableValueInstanceLog(
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout,
			String nombreVariableProceso, String valorVariableProceso) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<VariableInstanceLog> getVariableNameInstanceLog(
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout,
			String nameVariableProcess, boolean b) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ProcessInstanceLog> getActiveProcessInstances(String processId,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<ProcessInstanceLog> getProcessInstances(String processId,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
