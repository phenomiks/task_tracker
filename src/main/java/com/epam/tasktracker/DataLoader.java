package com.epam.tasktracker;

import com.epam.tasktracker.entities.Project;
import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.entities.User;
import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.List;

@Component
public class DataLoader {
    private final ProjectService projectService;
    private final TaskService taskService;
    private final UserService userService;

    @Autowired
    public DataLoader(ProjectService projectService, TaskService taskService, UserService userService) {
        this.projectService = projectService;
        this.taskService = taskService;
        this.userService = userService;
    }

    @PostConstruct
    public void init() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();

        String usersFile = "assets/users.json";
        String projectsFile = "assets/projects.json";
        String tasksFile = "assets/tasks.json";
        List<User> users = Arrays.asList(objectMapper.readValue(getFileFromResourceAsStream(usersFile), User[].class));
        List<Project> projects = Arrays.asList(objectMapper.readValue(getFileFromResourceAsStream(projectsFile), Project[].class));
        List<Task> tasks = Arrays.asList(objectMapper.readValue(getFileFromResourceAsStream(tasksFile), Task[].class));

        userService.save(users);
        projectService.save(projects);
        taskService.save(tasks);
    }

    private InputStream getFileFromResourceAsStream(String fileName) {
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(fileName);

        if (inputStream == null) {
            throw new IllegalArgumentException("File not found: " + fileName);
        }
        return inputStream;
    }
}
