package ec.gob.ambiente.jbpm.service;

import java.util.List;
import java.util.Map;

import org.kie.api.task.model.Task;
import org.kie.api.task.model.TaskSummary;

public class TaskBeanFacade {

	public void approveTask(String userName, Long taskId, Long processId,
			Map<String, Object> data, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout,
			String notificationService) {
		// TODO Auto-generated method stub
		
	}

	public Map<String, Object> getTaskVariables(Long taskId,
			String deploymentId, String userName, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public long actualTaskId(String userName, Long processInstanceId,
			String deploymentId, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return 0;
	}

	public List<TaskSummary> retrieveTaskList(String userName,
			String userName2, String deploymentId, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public void reassignTask(long taskId, String currentActorId,
			String targetUserId, String deploymentId, String userName,
			String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		
	}

	public TaskSummary actualTaskSummary(String userName,
			long processInstanceId, String deploymentId, String userPassword,
			String urlBusinessCentral, Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

	public Task getTaskById(long taskId, String deploymentId, String userName,
			String userPassword, String urlBusinessCentral,
			Integer remoteApiTimeout) {
		// TODO Auto-generated method stub
		return null;
	}

}
