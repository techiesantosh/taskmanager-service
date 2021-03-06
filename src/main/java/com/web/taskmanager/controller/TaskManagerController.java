package com.web.taskmanager.controller;

import com.web.taskmanager.model.JsonResponse;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controller to manage tasks
 */
@RestController
@RequestMapping("/taskmanager")
public class TaskManagerController {

    private static final Logger LOG = LoggerFactory.getLogger(TaskManagerController.class);

    @Autowired
    private TaskService taskService;

    /**
     * Creates a new task
     *
     * @param taskRequest
     * @return task
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public JsonResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
        LOG.info("TaskRequest => ", taskRequest);
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return new JsonResponse("", "SUCCESS", taskResponse);
    }

    /**
     * Delete an existing task
     *
     * @param taskId
     * @return JsonResponse
     * @throws Exception
     */

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteTask(@PathVariable("taskId") Long taskId) throws Exception {
        LOG.info("TaskId => ", taskId);
        taskService.deleteTask(taskId);
        return new JsonResponse("", "SUCCESS", null);
    }

    /**
     * To list all the task for the user
     *
     * @param username
     * @return list of task
     */

    @RequestMapping(value = "/task/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTasks(@PathVariable("username") String username) {
        LOG.info("username => ", username);
        List<TaskResponse> tasks = taskService.getTaskNames(username);
        return new JsonResponse("", "SUCCESS", tasks);
    }


    /**
     * To update existing task
     *
     * @param taskRequest
     * @param taskId
     * @return updated task
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse updateTask(@RequestBody TaskRequest taskRequest, @PathVariable("taskId") Long taskId) {
        LOG.info("TaskRequest => ", taskRequest);
        TaskResponse tasks = taskService.updateTask(taskRequest, taskId);
        return new JsonResponse("", "SUCCESS", tasks);
    }


}
