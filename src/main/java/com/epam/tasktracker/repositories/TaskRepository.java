package com.epam.tasktracker.repositories;

import com.epam.tasktracker.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
//    List<Task> findAllByUserIdAndProjectId(Long userId, Long projectId);
}
