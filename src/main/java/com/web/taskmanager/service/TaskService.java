package com.web.taskmanager.service;

import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;

import java.util.List;

/**
 * Service to manage tasks
 */
public interface TaskService {


    TaskResponse createTask(TaskRequest taskRequest);

    /**
     * @param username
     * @return
     */

    List<TaskResponse> getTaskNames(String username);


    /**
     * Updates the existing task
     *
     * @param taskRequest
     * @param taskId
     * @return the updated task
     */
    TaskResponse updateTask(TaskRequest taskRequest, Long taskId);

    /**
     * Deletes the task
     *
     * @param taskId
     * @throws Exception
     */
    void deleteTask(Long taskId) throws Exception;


}
