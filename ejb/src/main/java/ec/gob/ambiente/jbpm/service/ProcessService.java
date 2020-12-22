/*
 * Copyright 2014 MAGMASOFT Innovando Tecnologia
 * Todos los derechos reservados
 */
package ec.gob.ambiente.jbpm.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.jbpm.process.audit.ProcessInstanceLog;
import org.jbpm.process.audit.VariableInstanceLog;
import org.kie.api.runtime.process.ProcessInstance;
import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

import ec.gob.ambiente.enlisy.model.User;
import ec.gob.ambiente.util.Constant;
import ec.gob.ambiente.util.StatusProcess;

@Stateless
public class ProcessService {

	@EJB(lookup = Constant.JBPM_EJB_PROCESS_BEAN)
	private ProcessBeanFacade processBeanFacade;

	@EJB(lookup = Constant.JBPM_EJB_TASK_BEAN)
	private TaskBeanFacade taskBeanFacade;

	public synchronized long iniciarProceso(User user, String nombreProceso, Map<String, Object> parametros)
			throws JbpmException {
		if (parametros == null)
			parametros = new HashMap<String, Object>();
		return processBeanFacade.startProcess(nombreProceso, parametros, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public synchronized void aprobarTarea(User user, Long taskId, Long processId, Map<String, Object> data)
			throws JbpmException {
		taskBeanFacade.approveTask(user.getUserName(), taskId, processId, data, user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(),
				Constant.getNotificationService());
	}

	public void buscarAprobarActualTareaProceso(User user, Long processInstanceId, Map<String, Object> data)
			throws JbpmException {
		Long taskId = getCurrenTask(user, processInstanceId).getId();
		taskBeanFacade.approveTask(user.getUserName(), taskId, processInstanceId, data, user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(),
				Constant.getNotificationService());
	}

	public Map<String, Object> recuperarVariablesTarea(User user, Long taskId) throws JbpmException {
		Map<String, Object> variables = taskBeanFacade.getTaskVariables(taskId, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
		return variables;
	}

	public synchronized void modificarVariablesProceso(User user, long processInstanceID, Map<String, Object> params)
			throws JbpmException {
		ProcessInstanceLog proces = getProcessInstanceLog(user, processInstanceID);
		processBeanFacade.setProcessVariables(processInstanceID, params, proces.getExternalId(), user.getUserName(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public Map<String, Object> recuperarVariablesProceso(User user, long processInstanceID) throws JbpmException {
		return processBeanFacade.getProcessVariables(processInstanceID, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public long recuperarIdTareaActual(User user, Long processInstanceId) throws JbpmException {
		return taskBeanFacade.actualTaskId(user.getUserName(), processInstanceId, Constant.getDeploymentId(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public ProcessInstance getProcessInstance(User user, long processInstanceID) throws JbpmException {
		return processBeanFacade.getProcessInstance(processInstanceID, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public List<TaskSummary> getAllTaskAssigned(User user) throws JbpmException {
		return taskBeanFacade.retrieveTaskList(user.getUserName(), user.getUserName(), Constant.getDeploymentId(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());

	}

	public ProcessInstanceLog getProcessInstanceLog(User user, long processInstanceID) throws JbpmException {
		return processBeanFacade.getProcessInstanceLog(processInstanceID, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public synchronized void reasignarTarea(User user, long taskId, String currentActorId, String targetUserId)
			throws JbpmException {
		taskBeanFacade.reassignTask(taskId, currentActorId, targetUserId, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public List<TaskSummary> getTaskCompletedReserved(User user, Long processInstanceID) throws JbpmException {
		return processBeanFacade.getTaskSumaryByProcessId(processInstanceID, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public List<TaskSummary> getTaskReservedInProgress(User user, Long processInstanceID) throws JbpmException {
		return processBeanFacade.getTaskSumaryByProcessIdWithoutCompletedStatus(processInstanceID,
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public TaskSummary getCurrenTask(User user, long processInstanceId) throws JbpmException {
		return taskBeanFacade.actualTaskSummary(user.getUserName(), processInstanceId, Constant.getDeploymentId(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public synchronized void abortProcess(User user, long processInstanceId) throws JbpmException {
		processBeanFacade.abortProcess(processInstanceId, Constant.getDeploymentId(), user.getUserName(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public Map<String, Object> getProcesosProyecto(User user, String nombreVariableProceso,
			String valorVariableProceso) {
		List<VariableInstanceLog> listaVariables = processBeanFacade.getVariableValueInstanceLog(
				Constant.getDeploymentId(), user.getUserName(),
				user.getUserPassword(),// "f78b00ced8a7337426e2c91b76682713"
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nombreVariableProceso,
				valorVariableProceso);
		Map<String, Object> valoresRecuperados = new HashMap<String, Object>();
		for (int i = 0; i < listaVariables.size(); i++) {
			try {
				ProcessInstanceLog process = getProcessInstanceLog(user, listaVariables.get(i)
						.getProcessInstanceId());
				try {
					List<TaskSummary> listaTareas = obtenerEstados(user, process.getProcessInstanceId());
					for (int j = 0; j < listaTareas.size(); j++) {
						// t=tarea
						valoresRecuperados.put("t_" + process.getProcessInstanceId() + "_" + j, listaTareas.get(j));
					}
				} catch (JbpmException e) {
					e.printStackTrace();
				}
				// ep=estado proceso
				valoresRecuperados.put("ep_" + process.getProcessInstanceId() + "_" + i,
						StatusProcess.getNombreEstado(process.getStatus()));
			} catch (JbpmException e) {
				e.printStackTrace();
			}
		}
		return valoresRecuperados;
	}

	private List<TaskSummary> obtenerEstados(User user, Long processId) throws JbpmException {
		return processBeanFacade.getTaskSumaryByProcessIdWithoutCompletedStatus(processId,
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public List<VariableInstanceLog> getCurrentValuesVariableInstanceLogs(User user, String nameVariableProcess)
			throws JbpmException {

		Map<Long, VariableInstanceLog> variables = new HashMap<Long, VariableInstanceLog>();

		List<VariableInstanceLog> listVariable = processBeanFacade.getVariableNameInstanceLog(
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nameVariableProcess, false);

		for (VariableInstanceLog variableInstanceLog : listVariable) {
			if (variables.containsKey(variableInstanceLog.getProcessInstanceId())) {
				if (variables.get(variableInstanceLog.getProcessInstanceId()).getId() < variableInstanceLog.getId())
					variables.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
			} else
				variables.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
		}

		List<VariableInstanceLog> result = new ArrayList<VariableInstanceLog>();
		result.addAll(variables.values());

		return result;
	}

	public List<ProcessInstanceLog> getProcessInstancesLogsVariableValue(User user, String nameVariableProcess,
			String valueVariableProcess) throws JbpmException {
		List<ProcessInstanceLog> flowsByProject = new ArrayList<ProcessInstanceLog>();

		List<VariableInstanceLog> listVariable = processBeanFacade.getVariableValueInstanceLog(
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nameVariableProcess,
				valueVariableProcess);

		for (int i = 0; i < listVariable.size(); i++) {
			ProcessInstanceLog process = getProcessInstanceLog(user, listVariable.get(i).getProcessInstanceId());
			flowsByProject.add(process);
		}
		return flowsByProject;
	}

	public List<ProcessInstanceLog> getProcessInstancesLogsVariableValueUpdated(User user,
			String nameVariableProcess, String valueVariableProcess) throws JbpmException {
		List<ProcessInstanceLog> flowsByProject = new ArrayList<ProcessInstanceLog>();

		Map<Long, VariableInstanceLog> variables = new HashMap<Long, VariableInstanceLog>();

		Map<Long, VariableInstanceLog> variablesValues = new HashMap<Long, VariableInstanceLog>();

		List<VariableInstanceLog> listVariable = processBeanFacade.getVariableNameInstanceLog(
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nameVariableProcess, false);

		for (VariableInstanceLog variableInstanceLog : listVariable) {
			if (variables.containsKey(variableInstanceLog.getProcessInstanceId())) {
				if (variables.get(variableInstanceLog.getProcessInstanceId()).getId() < variableInstanceLog.getId())
					variables.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
			} else
				variables.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
		}

		List<VariableInstanceLog> listVariableValues = processBeanFacade.getVariableValueInstanceLog(
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nameVariableProcess,
				valueVariableProcess);

		for (VariableInstanceLog variableInstanceLog : listVariableValues) {
			if (variablesValues.containsKey(variableInstanceLog.getProcessInstanceId())) {
				if (variablesValues.get(variableInstanceLog.getProcessInstanceId()).getId() < variableInstanceLog
						.getId())
					variablesValues.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
			} else
				variablesValues.put(variableInstanceLog.getProcessInstanceId(), variableInstanceLog);
		}

		List<VariableInstanceLog> finalVariables = new ArrayList<VariableInstanceLog>();

		for (Long pocessInstanceId : variablesValues.keySet()) {
			if (variables.containsKey(pocessInstanceId))
				if (variables.get(pocessInstanceId).getId() >= variablesValues.get(pocessInstanceId).getId()
						&& variables.get(pocessInstanceId).getValue().equals(valueVariableProcess))
					finalVariables.add(variables.get(pocessInstanceId));
		}

		for (int i = 0; i < finalVariables.size(); i++) {
			ProcessInstanceLog process = getProcessInstanceLog(user, finalVariables.get(i).getProcessInstanceId());
			flowsByProject.add(process);
		}
		return flowsByProject;
	}

	public Map<Long, String> getProcessInstancesIdsVariableValue(User user, String nameVariableProcess,
			boolean procesosActivos) throws JbpmException {
		List<VariableInstanceLog> variables = processBeanFacade.getVariableNameInstanceLog(
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout(), nameVariableProcess,
				procesosActivos);
		Map<Long, String> processIdsVariableValue = new HashMap<Long, String>();
		for (VariableInstanceLog variable : variables)
			processIdsVariableValue.put(variable.getProcessInstanceId(), variable.getValue());
		return processIdsVariableValue;
	}

	public List<TaskSummary> getTaskBySelectFlow(User user, Long processId) throws JbpmException {
		List<TaskSummary> listaTareas = getTaskCompletedReserved(user, processId);
		return listaTareas;
	}

	public List<ProcessInstanceLog> getActiveProcessInstances(User user, String processId) throws JbpmException {
		return processBeanFacade.getActiveProcessInstances(processId, Constant.getDeploymentId(),
				user.getUserName(), user.getUserPassword(), Constant.getUrlBusinessCentral(),
				Constant.getRemoteApiTimeout());
	}

	public List<ProcessInstanceLog> getProcessInstances(User user, String processId) throws JbpmException {
		return processBeanFacade.getProcessInstances(processId, Constant.getDeploymentId(), user.getUserName(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

	public List<ProcessInstanceLog> getActiveProcessInstancesLogsVariableValue(User user, String processId,
			String variableName, Object variableValue) throws JbpmException {

		Map<String, Object> variables = new HashMap<String, Object>();
		variables.put(variableName, variableValue);

		return getActiveProcessInstancesLogsVariableValue(user, processId, variables);
	}

	public List<ProcessInstanceLog> getActiveProcessInstancesLogsVariableValue(User user, String processId,
			Map<String, Object> variablesToFind) throws JbpmException {
		List<ProcessInstanceLog> count = new ArrayList<ProcessInstanceLog>();
		List<ProcessInstanceLog> result = processBeanFacade.getActiveProcessInstances(processId,
				Constant.getDeploymentId(), user.getUserName(), user.getUserPassword(),
				Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());

		for (ProcessInstanceLog processInstanceLog : result) {
			Map<String, Object> variables = recuperarVariablesProceso(user,
					processInstanceLog.getProcessInstanceId());

			boolean found = true;
			for (String keyToFind : variablesToFind.keySet()) {
				if (variables.containsKey(keyToFind)
						&& variables.get(keyToFind) != null
						&& (variables.get(keyToFind).toString().equals(variablesToFind.get(keyToFind).toString()) || variables
								.get(keyToFind).equals(variablesToFind.get(keyToFind))))
					continue;
				else {
					found = false;
					break;
				}
			}

			if (found)
				count.add(processInstanceLog);
		}

		return count;
	}

	public Task getTaskById(User user, long taskId) throws JbpmException {
		return taskBeanFacade.getTaskById(taskId, Constant.getDeploymentId(), user.getUserName(),
				user.getUserPassword(), Constant.getUrlBusinessCentral(), Constant.getRemoteApiTimeout());
	}

//	public synchronized void asociarProcesoUser(User User, long processInstanceId) throws JbpmException {
//		asociarProcesoUserNombre(User, User.getUserName(), processInstanceId);
//	}
//
//	public synchronized void asociarProcesoUserNombre(User User, String nombreUser, long processInstanceId)
//			throws JbpmException {
//		Map<String, Object> user = new HashMap<String, Object>();
//		user.put(Constant.USUARIO_VISTA_MIS_PROCESOS, nombreUser);
//		modificarVariablesProceso(User, processInstanceId, user);
//	}
}
