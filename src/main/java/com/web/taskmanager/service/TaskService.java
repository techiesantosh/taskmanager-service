package com.web.taskmanager.service;

import com.web.taskmanager.dao.TaskSpecifications;
import com.web.taskmanager.exception.TaskNotFoundException;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.Task;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import com.web.taskmanager.util.DateUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Service to manage tasks
 */
@Service
public class TaskService {

    private static final Logger LOG = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Autowired
    private ModelMapper modelMapper;

    /**
     * @return TaskResponse
     */
    public TaskResponse createTask(TaskRequest taskRequest) {
        ApplicationUser applicationUser = applicationUserRepository
                .findByUsername(taskRequest.getUsername());

        Task task = new Task();
        Task parentTask = null;
        task.setTaskName(taskRequest.getTaskName());
        task.setStartDate(taskRequest.getStartDate());
        task.setEndDate(taskRequest.getEndDate());
        task.setPriority(taskRequest.getPriority());
        if (taskRequest.getParentTaskId() != null) {
            parentTask = getTask(taskRequest.getParentTaskId());
            task.setParentTask(parentTask);

        }
        task.setApplicationUser(applicationUser);
        task = taskRepository.save(task);

        return createTaskReponse(Arrays.asList(task)).get(0);


    }

    /**
     *
     */

    public List<TaskResponse> getTaskNames(String username) {
        ApplicationUser applicationUser = applicationUserRepository.findByUsername(username);
        if (applicationUser == null) {

            throw new UsernameNotFoundException(username);
        }
        List<Task> tasks = applicationUser.getTasks();
        return createTaskReponse(tasks);
    }

    /**
     *
     */

    public Map<Long, String> getTaskMap() {
        Map<Long, String> taskMap = taskRepository.findAll().stream().
                collect(Collectors.toMap(Task::getTaskId, Task::getTaskName));
        return taskMap;
    }

    public Task getTask(Long taskId) {
        return taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("TaskId " + taskId + " doesn't exists"));
    }

    public List<TaskResponse> searchTasks(TaskRequest taskRequest) {
        List<Task> taskList = taskRepository
                .findAll(TaskSpecifications.hasTaskName(taskRequest.getTaskName()));
        return createTaskReponse(taskList);

    }

    /**
     *
     */
    public List<TaskResponse> createTaskReponse(List<Task> taskList) {
        List<TaskResponse> taskResponses = new ArrayList<>();
        for (Task task : taskList) {
            TaskResponse taskResponse = new TaskResponse();
            taskResponse.setTaskId(task.getTaskId());
            taskResponse.setTaskName(task.getTaskName());
            taskResponse.setPriority(task.getPriority());
            if (task.getParentTask() != null) {
                taskResponse.setParentTaskName(task.getParentTask().getTaskName());
                taskResponse.setParentTaskId(task.getParentTask().getTaskId());
            }
            taskResponse.setStartDate(DateUtil.formatDate(task.getStartDate()));
            taskResponse.setEndDate(DateUtil.formatDate(task.getEndDate()));
            taskResponses.add(taskResponse);
        }
        return taskResponses;
    }

    /**
     *
     */
    public TaskResponse updateTask(TaskRequest taskRequest, Long taskId) {
        TaskResponse taskResponse = null;

        Task task1 = taskRepository.findById(taskId).map(task -> {
            task.setTaskName(taskRequest.getTaskName());
            if (taskRequest.getParentTaskId() != null) {
                task.setParentTask(getTask(taskRequest.getParentTaskId()));

            }

            task.setPriority(taskRequest.getPriority());
            task.setStartDate(taskRequest.getStartDate());
            task.setEndDate(taskRequest.getEndDate());
            return taskRepository.save(task);
        }).orElseThrow(
                () -> new TaskNotFoundException(String.format("TaskId %s doesn't exists", taskId)));

        if (task1 != null) {
            taskResponse = modelMapper.map(task1, TaskResponse.class);
            if (task1.getParentTask() != null) {
                taskResponse.setParentTaskName(task1.getParentTask().getTaskName());
                taskResponse.setParentTaskId(task1.getParentTask().getTaskId());
            }


        }

        return taskResponse;
    }

    /**
     *
     */
    public void deleteTask(Long taskId) throws Exception {
        try {
            taskRepository.deleteById(taskId);
        } catch (DataAccessException dae) {

            LOG.error("Error = {}", dae.getLocalizedMessage());

            throw new TaskNotFoundException(dae.getLocalizedMessage());
        }

    }

}
