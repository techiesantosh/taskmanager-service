package com.web.taskmanager.service;

import com.web.taskmanager.dao.TaskSpecifications;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.Task;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import com.web.taskmanager.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ApplicationUserRepository applicationUserRepository;

  public List<TaskResponse> createTask(TaskRequest taskRequest) {
    ApplicationUser applicationUser = applicationUserRepository.findByUsername(taskRequest.getUsername());

    Task task = new Task();
    Task parentTask = null;
    List<Task> taskList = new ArrayList<>();
    task.setTaskName(taskRequest.getTaskName());
    task.setStartDate(taskRequest.getStartDate());
    task.setEndDate(taskRequest.getEndDate());
    task.setPriority(taskRequest.getPriority());
    if (taskRequest.getParentTaskId() != null) {
      parentTask = getTask(taskRequest.getParentTaskId());
      task.setParentTask(parentTask);

    }
    task.setApplicationUser(applicationUser);
    task= taskRepository.save(task);
    taskList.add(task);

    return  createTaskReponse(taskList);


  }

  public List<TaskResponse> getTaskNames() {
    List<Task> tasks = taskRepository.
        findAll().stream().collect(Collectors.toList());
    return createTaskReponse(tasks);
  }

  public Map<Long, String> getTaskMap() {
    Map<Long, String> taskMap = taskRepository.findAll().stream().
        collect(Collectors.toMap(Task::getTaskId, Task::getTaskName));
    return taskMap;
  }

  public Task getTask(Long taskId) {
    return taskRepository.findById(taskId).orElse(null);
  }

  public List<TaskResponse> searchTasks(TaskRequest taskRequest) {
    List<Task> taskList = taskRepository
        .findAll(TaskSpecifications.hasTaskName(taskRequest.getParentTask()));
    return createTaskReponse(taskList);

  }

  public List<TaskResponse> createTaskReponse(List<Task> taskList){
    List<TaskResponse> taskResponses = new ArrayList<>();
    for (Task task:taskList) {
      TaskResponse taskResponse =new TaskResponse();
      taskResponse.setTaskId(  task.getTaskId());
      taskResponse.setTaskName(task.getTaskName());
      taskResponse.setPriority(task.getPriority());
      if(task.getParentTask() !=null){
        taskResponse.setParentTask(task.getParentTask().getTaskName());
        taskResponse.setParentTaskId(task.getParentTask().getTaskId());
      }
      taskResponse.setStartDate(DateUtil.formatDate(task.getStartDate()));
      taskResponse.setEndDate(DateUtil.formatDate(task.getEndDate()));
      taskResponses.add(taskResponse);
    }
    return taskResponses;
  }
}
