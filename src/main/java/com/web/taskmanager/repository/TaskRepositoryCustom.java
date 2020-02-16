package com.web.taskmanager.repository;

import com.web.taskmanager.model.Task;
import java.util.List;

public interface TaskRepositoryCustom {

    List<Task> findTaskByTaskNameAndParentTask(String taskName, String parentTask);

}
