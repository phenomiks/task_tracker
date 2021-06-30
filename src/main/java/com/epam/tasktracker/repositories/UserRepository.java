package com.epam.tasktracker.repositories;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u FROM User u JOIN FETCH u.tasks WHERE u.id = (:id)")
    List<Task> findByIdAndFetch(Long id);

    Collection<User> findAllByProjectId(Long projectId);
}
