package com.web.taskmanager.repository;

import com.web.taskmanager.model.ApplicationUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class ApplicationUserRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ApplicationUserRepository userRepository;


    @Test
    @Rollback(false)
    public void findByUsername() {

        ApplicationUser user = new ApplicationUser();
        user.setUsername("chougule");
        user.setPassword("passw0rd");

        entityManager.persistAndFlush(user);

        ApplicationUser searchUser = userRepository.findByUsername("chougule");

        assertThat(searchUser.getUsername()).isEqualTo(user.getUsername());


    }
}