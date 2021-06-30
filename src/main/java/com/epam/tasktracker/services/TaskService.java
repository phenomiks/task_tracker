package com.epam.tasktracker.services;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task createTask(String title, String description) {
        return new Task().builder()
                .setTitle(title)
                .setDescription(description)
                .build();
    }

    public void save(String title, String description) {
        Task task = createTask(title, description);
        taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public Optional<Task> findById(Long id) {
        return taskRepository.findById(id);
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }
}
