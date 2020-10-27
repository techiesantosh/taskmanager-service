package com.web.taskmanager.model;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "TASK")
public class Task {

    @Id
    @Column(name = "TASK_ID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long taskId;

    @Column(name = "TASKNAME")
    private String taskName;


    @ManyToOne(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY)
    @JoinColumn(name = "PARENT_ID")
    private Task parentTask;

    @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
    @OneToMany(mappedBy = "parentTask", cascade = {CascadeType.REMOVE}, orphanRemoval = true)
    private Set<Task> childTasks = new HashSet<Task>();

    @Column(name = "START_DATE")
    // @Temporal(TemporalType.DATE)
    private LocalDate startDate;

    // @Temporal(TemporalType.DATE)
    @Column(name = "END_DATE")
    private LocalDate endDate;

    @Column(name = "PRIORITY")
    private int priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID", nullable = false)
    private ApplicationUser applicationUser;


    public Task() {

    }

    public Task(String taskName, Task parentTask,
                Set<Task> childTasks, int priority, LocalDate startDate, LocalDate endDate) {

        this.taskName = taskName;
        this.parentTask = parentTask;
        this.priority = priority;
        // this.childTasks = childTasks;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getTaskId() {
        return taskId;
    }

    public void setTaskId(Long taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }


    public Task getParentTask() {
        return parentTask;
    }

    public void setParentTask(Task parentTask) {
        this.parentTask = parentTask;
    }

    public Set<Task> getChildTasks() {
        return childTasks;
    }

    public void setChildTasks(Set<Task> childTasks) {
        this.childTasks = childTasks;
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

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }


    public ApplicationUser getApplicationUser() {
        return applicationUser;
    }

    public void setApplicationUser(ApplicationUser applicationUser) {
        this.applicationUser = applicationUser;
    }

    public Long getParentTaskId() {
        if (this.parentTask != null) {
            return this.parentTask.getTaskId();
        }

        return null;

    }


    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Task task = (Task) o;
        return priority == task.priority &&

                taskName.equals(task.taskName) &&

                startDate.equals(task.startDate) &&
                endDate.equals(task.endDate);

    }

    @Override
    public int hashCode() {
        return Objects.hash(taskName, startDate, endDate, priority
        );
    }
}