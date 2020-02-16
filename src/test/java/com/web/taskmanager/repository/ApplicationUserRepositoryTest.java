package com.web.taskmanager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.web.taskmanager.model.ApplicationUser;
import org.junit.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@DataJpaTest
@Disabled
public class ApplicationUserRepositoryTest {

  @Autowired
  private TestEntityManager entityManager;

  @Autowired
  private  ApplicationUserRepository userRepository;


  @Test
  public void findBywUsername() {

    ApplicationUser user = new ApplicationUser();
    user.setUsername("santosh");

    entityManager.persistAndFlush(user);

    ApplicationUser searchUser = userRepository.findByUsername("santosh");

    assertThat(searchUser.getUsername()).isEqualTo(user.getUsername());


  }
}