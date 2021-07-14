package com.epam.tasktracker.repositories;

import com.epam.tasktracker.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Collection<User> findAllByProjectId(Long projectId);
}
