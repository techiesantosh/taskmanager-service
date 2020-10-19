package com.web.taskmanager.controller;

import com.web.taskmanager.model.JsonResponse;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.service.TaskService;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

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
     * @param taskRequest
     * @return
     */
    @RequestMapping(value = "/task", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public JsonResponse createTask(@RequestBody @Valid TaskRequest taskRequest) {
        TaskResponse taskResponse = taskService.createTask(taskRequest);
        return new JsonResponse("", "SUCCESS", taskResponse);
    }

    /**
     * @param taskId
     * @return
     * @throws Exception
     */

    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse deleteTask(@PathVariable("taskId") Long taskId) throws Exception {
        taskService.deleteTask(taskId);
        return new JsonResponse("", "SUCCESS", null);
    }

    /**
     * @param username
     * @return
     */

    @RequestMapping(value = "/task/{username}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse getTasks(@PathVariable("username") String username) {

        List<TaskResponse> tasks = taskService.getTaskNames(username);
        return new JsonResponse("", "SUCCESS", tasks);
    }



    /**
     * @param taskRequest
     * @param taskId
     * @return
     */
    @RequestMapping(value = "/task/{taskId}", method = RequestMethod.PUT, produces = MediaType.APPLICATION_JSON_VALUE)
    public JsonResponse updateTasks(@RequestBody TaskRequest taskRequest, @PathVariable("taskId") Long taskId) {

        TaskResponse tasks = taskService.updateTask(taskRequest, taskId);
        return new JsonResponse("", "SUCCESS", tasks);
    }


}
