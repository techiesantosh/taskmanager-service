package com.web.taskmanager.model;

import java.time.LocalDate;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="TASK")
public class Task {

  @Id
  @Column(name="TASK_ID")
  @GeneratedValue(strategy= GenerationType.AUTO)
  private Long taskId;

  @Column(name="TASKNAME")
  private String taskName;



  @ManyToOne(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
  @JoinColumn(name="PARENT_ID")
  private Task parentTask;

//  @JsonSerialize(include = JsonSerialize.Inclusion.NON_EMPTY)
//  @OneToMany(mappedBy="parentTask")
//  private Set<Task> childTasks = new HashSet<Task>();

  @Column(name = "START_DATE")
 // @Temporal(TemporalType.DATE)
  private LocalDate  startDate;

 // @Temporal(TemporalType.DATE)
  @Column(name = "END_DATE")
  private LocalDate endDate;

  @Column(name = "PRIORITY")
  private int priority;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "USER_ID", nullable = false)
  private ApplicationUser applicationUser;



  public Task(){

  }

  public Task(Long taskId, String taskName, Task parentTask,
      Set<Task> childTasks, LocalDate startDate, LocalDate endDate) {
    this.taskId = taskId;
    this.taskName = taskName;
    this.parentTask = parentTask;
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

 /* public Set<Task> getChildTasks() {
    return childTasks;
  }

  public void setChildTasks(Set<Task> childTasks) {
    this.childTasks = childTasks;
  }*/

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
}