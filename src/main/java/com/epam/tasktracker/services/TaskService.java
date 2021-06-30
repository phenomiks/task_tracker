package com.epam.tasktracker.services;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task createTask(String title, String description) {
        Task task = new Task();
        task.setTitle(title);
        task.setDescription(description);
        return task;
    }

    public void save(String title, String description) {
        Task task = createTask(title, description);
        taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    public void deleteById(Long id) {
        taskRepository.deleteById(id);
    }

//    public List<Task> findAllByUserIdAndProjectId(Long userId, Long projectId) {
//        return taskRepository.findAllByUserIdAndProjectId(userId, projectId);
//    }
}
