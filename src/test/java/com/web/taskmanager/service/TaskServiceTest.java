package com.web.taskmanager.service;

import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.Task;
import com.web.taskmanager.model.TaskRequest;
import com.web.taskmanager.model.TaskResponse;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
public class TaskServiceTest {

    @Autowired
    private TaskService taskService;

    @MockBean
    private TaskRepository taskRepository;

    @MockBean
    private ApplicationUserRepository userRepository;

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
    public void updateTask() {
        TaskRequest taskRequest = new TaskRequest("Email", 2, "Select Agenda", LocalDate.now(),
                LocalDate.now().plusDays(4));
        taskRequest.setParentTaskId(3L);
        TaskResponse taskResponse = taskService.updateTask(taskRequest, 1L);
        assertThat(taskResponse.getTaskName()).isEqualTo(taskRequest.getTaskName());
    }

    @Test
    public void deleteTask() throws Exception {

            taskService.deleteTask(1L);
            Mockito.verify(taskRepository, Mockito.times(1)).deleteById(1L);


    }

    @TestConfiguration
    static class TaskServiceTestContextConfiguration {

        @Bean
        public TaskService taskService() {
            return new TaskServiceImpl();
        }

        @Bean
        public ModelMapper modelMapper() {
            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setSkipNullEnabled(true);
            return mapper;
        }
    }


}