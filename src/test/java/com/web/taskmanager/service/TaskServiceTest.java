package com.web.taskmanager.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.Task;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.Ignore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Disabled
public class TaskServiceTest {

  @Autowired
  private TaskService taskService;

  @MockBean
  private TaskRepository taskRepository;

  @MockBean
  private ApplicationUserRepository userRepository;

  @MockBean
  private ModelMapper modelMapper;

  @BeforeEach
  public void setUp() {

    ApplicationUser user = new ApplicationUser();
    Task parentTask = new Task("Select Agenda", null, null, 2, LocalDate.now(),
        LocalDate.now().plusDays(4));
    parentTask.setTaskId(3L);
    user.setUsername("santosh");
    Task task = new Task("Email", parentTask, null, 2, LocalDate.now(),
        LocalDate.now().plusDays(4));
    task.setTaskId(1L);
    Task otherTask = new Task("Meeting", parentTask, null, 2, LocalDate.now(),
        LocalDate.now().plusDays(4));
    otherTask.setTaskId(2L);
    List<Task> tasks = new ArrayList<>();
    tasks.add(task);
    tasks.add(otherTask);
    user.setTasks(tasks);
    task.setApplicationUser(user);

    Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(user);

    Mockito.when(taskRepository.save(task)).thenReturn(task);
    Mockito.when(taskRepository.findById(task.getTaskId())).thenReturn(Optional.of(task));
    Mockito.when(taskRepository.findById(parentTask.getTaskId()))
        .thenReturn(Optional.of(parentTask));

    Mockito.when(taskRepository.findAll()).thenReturn(tasks);


  }

  @Test
  public void createTask() {
    TaskRequest taskRequest = new TaskRequest("Email", 2, null, LocalDate.now(),
        LocalDate.now().plusDays(4));
    taskRequest.setTaskId(1L);
    taskRequest.setUsername("santosh");

    TaskResponse taskResponse = taskService.createTask(taskRequest);

    assertThat(taskResponse.getTaskName()).isEqualTo("Email");
  }

  @Test
  public void getTaskNames() {

    List<TaskResponse> taskResponses = taskService.getTaskNames("santosh");
    assertThat(taskResponses.get(0).getTaskName()).isEqualTo("Email");
  }

  @Test
  public void getTaskMap() {

    Map<Long, String> taskMap = taskService.getTaskMap();
    assertThat(taskMap.get(1L)).isEqualTo("Email");
  }

  @Test
  public void getTask() {
    Task task = taskService.getTask(1L);
    assertThat(task.getTaskName()).isEqualTo("Email");

  }

  @Test
  @Ignore
  public void searchTasks() {
    TaskRequest taskRequest = new TaskRequest("Meeting", 2, null, LocalDate.now(),
        LocalDate.now().plusDays(4));

    List<TaskResponse> taskResponses = taskService.searchTasks(taskRequest);

    assertThat(taskResponses.get(1).getTaskName()).isEqualTo(taskRequest.getTaskName());
  }

  @Test
  @Ignore
  public void updateTask() {
    TaskRequest taskRequest = new TaskRequest("Email", 2, "Select Agenda", LocalDate.now(),
        LocalDate.now().plusDays(4));
    taskRequest.setParentTaskId(3L);
    TaskResponse taskResponse = taskService.updateTask(taskRequest, 1L);
    assertThat(taskResponse.getTaskName()).isEqualTo(taskRequest.getTaskName());
  }

  @Test
  @Ignore
  public void deleteTask() {
    try {
      taskService.deleteTask(1L);
      Mockito.verify(taskRepository,Mockito.times(1)).deleteById(1L);
    }catch (Exception e){
      System.out.printf("exception ={}" ,e);
    }



  }

  @TestConfiguration
  static class TaskServiceTestContextConfiguration {

    @Bean
    public TaskService taskService() {
      return new TaskService();
    }
  }


}