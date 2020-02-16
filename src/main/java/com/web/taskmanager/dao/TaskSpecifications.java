package com.web.taskmanager.dao;

import com.web.taskmanager.model.Task;
import java.util.Date;
import org.springframework.data.jpa.domain.Specification;

public class TaskSpecifications {


    public static Specification<Task> hasTaskName(String taskName) {
        return (task, cq, cb) -> cb.equal(task.get("taskName"), taskName);
    }

    public static Specification<Task> hasParentTask(String parentTask) {
        return (task, cq, cb) -> cb.like(task.get("parentTask"), parentTask);
    }

    public static Specification<Task> priorityBetween(int priorityFrom, int priorityTo) {
        return (task, cq, cb) -> cb.between(task.get("priority"), priorityFrom, priorityTo);
    }


    public static Specification<Task> hasStartDate(Date startDate) {
        return (task, cq, cb) -> cb.equal(task.get("startDate"), startDate);
    }


    public static Specification<Task> hasEndDate(Date endDate) {
        return (task, cq, cb) -> cb.equal(task.get("endDate"), endDate);
    }


}
