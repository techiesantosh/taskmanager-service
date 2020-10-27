package com.web.taskmanager.repository;

import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.model.Task;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class TaskRepositoryIntegrationTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationUserRepository userRepository;

    @Test
    public void whenFindById_thenReturnTask() {

        Task task = new Task("Mail", null, null, 2, LocalDate.now(), LocalDate.now().plusDays(4));
        ApplicationUser searchUser = userRepository.findByUsername("chougule");
        task.setApplicationUser(searchUser);
        entityManager.persist(task);
        entityManager.flush();

        Optional<Task> foundTask = taskRepository.findById(1L);


        assertThat(foundTask.get().getTaskName()).isEqualTo(task.getTaskName());


    }

}