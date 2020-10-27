package com.web.taskmanager.service;

import com.web.taskmanager.exception.UserAlreadyExistsException;
import com.web.taskmanager.model.ApplicationUser;
import com.web.taskmanager.repository.ApplicationUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private ApplicationUserRepository applicationUserRepository;

    @Override
    public void createUser(ApplicationUser user) {
        try {
            applicationUserRepository.save(user);
        } catch (DataIntegrityViolationException dataIntegrityViolationException) {
            LOG.error("Exception => ", dataIntegrityViolationException);
            throw new UserAlreadyExistsException("Username =" +user.getUsername() +" already exists");

        } catch (DataAccessException dataAccessException) {
            LOG.error("Exception => ", dataAccessException);
            throw dataAccessException;


        }

    }
}
