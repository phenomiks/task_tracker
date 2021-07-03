package com.epam.tasktracker.services;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    private Task createTask(String title, String description, Long leadTime) {
        return new Task().builder()
                .setTitle(title)
                .setDescription(description)
                .setLeadTime(leadTime)
                .build();
    }

    public void save(String title, String description, Long leadTime) {
        Task task = createTask(title, description, leadTime);
        task.setParentTask(null);
        taskRepository.save(task);
    }

    @Transactional
    public boolean save(String title, String description, Long leadTime, Long parentId) {
        Task subtask = createTask(title, description, leadTime);
        Optional<Task> parentTask = findById(parentId);

        if (parentTask.isPresent()) {
            subtask.setParentTask(parentTask.get());
            parentTask.get().getSubtasks().add(subtask);
            taskRepository.save(parentTask.get());
            taskRepository.save(subtask);
            return true;
        }

        return false;
    }

    @Transactional
    public void save(Collection<Task> tasks) {
        tasks.forEach(taskRepository::save);
    }

    public void save(Task task) {
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
