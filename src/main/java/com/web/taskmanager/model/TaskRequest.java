package com.web.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDate;
import javax.validation.constraints.NotNull;

public class TaskRequest {

  @NotNull
  private  String username;

  @NotNull
  private String taskName;

  @NotNull
  private int priority;

  private String parentTask;

  private Long taskId;

  private Long parentTaskId;

  @NotNull
  @JsonFormat(pattern = "MM/dd/yyyy")
  private LocalDate startDate;

  @NotNull
  @JsonFormat(pattern = "MM/dd/yyyy")
  private LocalDate endDate;

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
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

  public TaskRequest() {

  }

  public TaskRequest(String taskName, int priority, String parentTask, LocalDate startDate,
      LocalDate endDate) {
    this.taskName = taskName;
    this.priority = priority;
    this.parentTask = parentTask;
    this.startDate = startDate;
    this.endDate = endDate;
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

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }
}
