package com.web.taskmanager.controller;

import com.web.taskmanager.model.JsonResponse;
import com.web.taskmanager.model.Task;
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

@RestController
@RequestMapping("/taskmanager")
public class TaskManagerController {

  private static final Logger LOG = LoggerFactory.getLogger(TaskManagerController.class);

  @Autowired
  private TaskService taskService;

  @RequestMapping(value = "/createtask", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  public JsonResponse createTask( @RequestBody @Valid TaskRequest taskRequest) {
    List<TaskResponse> tasks = taskService.createTask(taskRequest);
    return new JsonResponse("", "SUCCESS", tasks);
  }

  @RequestMapping(value = "/deletetasks/{taskId}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
  public JsonResponse deleteTask(@PathVariable("taskId") String taskId) {
    return new JsonResponse("", "SUCCESS", null);
  }

  @RequestMapping(value = "/gettasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
  public JsonResponse getTasks() {

    List<TaskResponse> tasks= taskService.getTaskNames();
    return new JsonResponse("", "SUCCESS", tasks);
  }

  @RequestMapping(value = "/searchtasks", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
  public JsonResponse searchTasks(@RequestBody TaskRequest taskRequest) {

    List<TaskResponse> tasks= taskService.searchTasks(taskRequest);
    return new JsonResponse("", "SUCCESS", tasks);
  }




}
