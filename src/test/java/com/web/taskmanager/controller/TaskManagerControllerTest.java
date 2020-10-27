package com.web.taskmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.repository.ApplicationUserRepository;
import com.web.taskmanager.repository.TaskRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                .value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].taskId")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].taskName")
                        .value("Setup-Meeting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].priority")
                        .value(3))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].startDate")
                .value("2020-12-12"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result[0].endDate")
                        .value("2020-12-24"));

    }

    @Test
    @Order(2)
    public void updateTask() throws Exception {
        MockTaskRequest taskRequest = new MockTaskRequest();
        taskRequest.setUsername("root");
        taskRequest.setTaskName("Setup-Meeting");
        taskRequest.setPriority(3);
        taskRequest.setStartDate("2020-12-12");
        taskRequest.setEndDate("2020-12-24");

        mockMvc.perform(put("/taskmanager/task/{taskId}", 1)
                        .header("Authorization", "Bearer " + JWT)
                        .content(asJsonString(taskRequest))
                        .contentType(MediaType.APPLICATION_JSON)).andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskId")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskName")
                        .value("Setup-Meeting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.priority")
                        .value(3));
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


        mockMvc.perform(
                post("/taskmanager/task")
                        .header("Authorization", "Bearer " + JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(taskRequest))
        ).andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskId")
                        .value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskName")
                        .value("Mail"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.priority")
                        .value(3));

        MockTaskRequest secondTaskRequest = new MockTaskRequest();
        secondTaskRequest.setUsername("root");
        secondTaskRequest.setTaskName("Stand-up Meeting");
        secondTaskRequest.setPriority(6);
        secondTaskRequest.setStartDate("2020-12-12");
        secondTaskRequest.setEndDate("2020-12-30");


        mockMvc.perform(
                post("/taskmanager/task")
                        .header("Authorization", "Bearer " + JWT)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(secondTaskRequest))
        ).andDo(print()).andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("SUCCESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskId")
                        .value(2))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.taskName")
                        .value("Stand-up Meeting"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.result.priority")
                        .value(6));
    }

    @Test
    @Order(4)
    public void deleteTask() throws Exception {
        this.mockMvc
                .perform(delete("/taskmanager/task/{taskId}", 1)
                        .header("Authorization", "Bearer " + JWT))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message")
                        .value("SUCCESS"));
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