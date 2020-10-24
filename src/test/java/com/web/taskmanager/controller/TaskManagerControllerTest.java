package com.web.taskmanager.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@TestMethodOrder(OrderAnnotation.class)
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestInstance(Lifecycle.PER_CLASS)
public class TaskManagerControllerTest {

  static final long EXPIRATIONTIME = 864_000_000; // 10 days

  static final String SECRET = "ThisIsASecret";

  static final String TOKEN_PREFIX = "Bearer";

  static final String HEADER_STRING = "Authorization";

  private static String JWT;

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  @Autowired
  private TaskRepository taskRepository;

  @Autowired
  private ApplicationUserRepository userRepository;

  /*@Test
  public void searchTasks() throws Exception {
    this.mockMvc.perform(post("/searchtasks/{taskId}", 1)).andExpect(status().isAccepted())

  }*/

  public static String asJsonString(final Object obj) {
    try {
      return new ObjectMapper().writeValueAsString(obj);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  @Order(3)
  public void getTasks() throws Exception {
    this.mockMvc.perform(get("/taskmanager/task/{username}",
        "root")
        .header("Authorization", "Bearer " + JWT)).andDo(print())
        .andExpect(status().isOk());

  }

  @Test
  @Order(2)
  public void updateTasks() throws Exception {
    MockTaskRequest taskRequest = new MockTaskRequest();
    taskRequest.setUsername("root");
    taskRequest.setTaskName("Setup-Meeting");
    taskRequest.setPriority(3);
    taskRequest.setStartDate("2020-12-12");
    taskRequest.setEndDate("2020-12-24");

    mockMvc
        .perform(put("/taskmanager/task/{taskId}", 1)
            .header("Authorization", "Bearer " + JWT)
            .content(asJsonString(taskRequest))
            .contentType(MediaType.APPLICATION_JSON)).andDo(print())
        .andExpect(status().isOk());

  }

  @BeforeAll
  public void setUp() throws Exception {

    JWT = Jwts.builder()
        .setSubject("root")
        .setExpiration(
            new Date(System.currentTimeMillis() + EXPIRATIONTIME))
        .signWith(SignatureAlgorithm.HS512, SECRET)
        .compact();

    ApplicationUser user = new ApplicationUser();
    user.setUsername("root");
    userRepository.save(user);


  }

  @Test
  @Order(1)
  public void createTask() throws Exception {
    MockTaskRequest taskRequest = new MockTaskRequest();
    taskRequest.setUsername("root");
    taskRequest.setTaskName("Mail");
    taskRequest.setPriority(3);
    taskRequest.setStartDate("2019-12-12");
    taskRequest.setEndDate("2019-12-25");
    ApplicationUser applicationUser = new ApplicationUser();
    applicationUser.setUsername("root");
    applicationUser.setPassword("pass");

    mockMvc.perform(
        post("/taskmanager/task")
            .header("Authorization", "Bearer " + JWT)
            .contentType(MediaType.APPLICATION_JSON)
            .content(asJsonString(taskRequest))
    ).andDo(print()).andExpect(status().isCreated());
  }

  @Test
  @Order(4)
  public void deleteTask() throws Exception {
    this.mockMvc
        .perform(delete("/taskmanager/task/{taskId}", 1)
            .header("Authorization", "Bearer " + JWT))
        .andExpect(status().isOk());
  }

  private class MockTaskRequest {

    private String username;

    private String taskName;

    private int priority;

    private String parentTask;

    private Long taskId;

    private Long parentTaskId;

    private String startDate;

    private String endDate;

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public String getTaskName() {
      return taskName;
    }

    public void setTaskName(String taskName) {
      this.taskName = taskName;
    }

    public int getPriority() {
      return priority;
    }

    public void setPriority(int priority) {
      this.priority = priority;
    }

    public String getParentTask() {
      return parentTask;
    }

    public void setParentTask(String parentTask) {
      this.parentTask = parentTask;
    }

    public Long getTaskId() {
      return taskId;
    }

    public void setTaskId(Long taskId) {
      this.taskId = taskId;
    }

    public Long getParentTaskId() {
      return parentTaskId;
    }

    public void setParentTaskId(Long parentTaskId) {
      this.parentTaskId = parentTaskId;
    }

    public String getStartDate() {
      return startDate;
    }

    public void setStartDate(String startDate) {
      this.startDate = startDate;
    }

    public String getEndDate() {
      return endDate;
    }

    public void setEndDate(String endDate) {
      this.endDate = endDate;
    }
  }
}