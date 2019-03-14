package com.web.taskmanager.repository;

import com.web.taskmanager.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
@Repository
public interface TaskRepository extends JpaRepository<Task, Long> ,TaskRepositoryCustom,

  JpaSpecificationExecutor<Task>

  {



}
