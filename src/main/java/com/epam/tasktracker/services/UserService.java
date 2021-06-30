package com.epam.tasktracker.services;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.entities.User;
import com.epam.tasktracker.repositories.TaskRepository;
import com.epam.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Autowired
    public UserService(UserRepository userRepository, TaskRepository taskRepository) {
        this.userRepository = userRepository;
        this.taskRepository = taskRepository;
    }

    private User createUser(String firstname, String lastname, String phone) {
        return new User().builder()
                .setFirstname(firstname)
                .setLastname(lastname)
                .setPhone(phone)
                .build();
    }

    public void save(String firstname, String lastname, String phone) {
        User user = createUser(firstname, lastname, phone);
        userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Transactional
    public void addTaskForUser(Long userId, Long taskId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Task> task = taskRepository.findById(taskId);
        if (user.isPresent() && task.isPresent()) {
            user.get().getTasks().add(task.get());
            task.get().setUser(user.get());
            taskRepository.save(task.get());
            userRepository.save(user.get());
        } else {
            throw new IllegalArgumentException("Unknown User or Task");
        }
    }

    public Collection<Task> getAllTasksFromUser(Long userId) {
        return taskRepository.findAllByUserId(userId);
    }
}
