package com.web.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)

public class TaskResponse {

  private Long taskId;

  private String taskName;

  private int priority;

  private String parentTask;

  private Long parentTaskId;

  @JsonFormat(pattern = "MM/dd/yyyy")
  private String startDate;

  @JsonFormat(pattern = "MM/dd/yyyy")
  private String endDate;

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

  public TaskResponse(){

  }

  public TaskResponse(Long taskId, String taskName, int priority, Long parentTaskId,
      String parentTask,
      String startDate, String endDate) {
    this.taskName = taskName;
    this.priority = priority;
    this.parentTask = parentTask;
    this.taskId = taskId;
    this.parentTaskId = parentTaskId;
    this.startDate = startDate;
    this.endDate = endDate;
  }
}
