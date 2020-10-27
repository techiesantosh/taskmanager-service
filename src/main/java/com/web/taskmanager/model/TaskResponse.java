package com.web.taskmanager.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_NULL)

public class TaskResponse {

    private Long taskId;

    private String taskName;

    private int priority;

    private String parentTaskName;

    private Long parentTaskId;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private String endDate;

    public TaskResponse() {

    }

    public TaskResponse(Long taskId, String taskName, int priority, Long parentTaskId,
                        String parentTask,
                        String startDate, String endDate) {
        this.taskName = taskName;
        this.priority = priority;
        this.parentTaskName = parentTask;
        this.taskId = taskId;
        this.parentTaskId = parentTaskId;
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

    public String getParentTaskName() {
        return parentTaskName;
    }

    public void setParentTaskName(String parentTaskName) {
        this.parentTaskName = parentTaskName;
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

    @JsonFormat(pattern = "yyyy-MM-dd")

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(pattern = "yyyy-MM-dd")

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
}
