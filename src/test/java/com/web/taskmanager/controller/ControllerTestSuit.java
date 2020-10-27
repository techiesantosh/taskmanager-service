package com.web.taskmanager.controller;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;

@RunWith(JUnitPlatform.class)
@SelectClasses({UserControllerTest.class, TaskManagerControllerTest.class})
public class ControllerTestSuit {

}
