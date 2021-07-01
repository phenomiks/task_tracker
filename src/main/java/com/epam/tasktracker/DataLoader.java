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
import java.io.File;
import java.io.IOException;
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
        File usersFile = new File("src/main/resources/assets/users.json");
        File projectsFile = new File("src/main/resources/assets/projects.json");
        File tasksFile = new File("src/main/resources/assets/tasks.json");

        List<User> users = Arrays.asList(objectMapper.readValue(usersFile, User[].class));
        List<Project> projects = Arrays.asList(objectMapper.readValue(projectsFile, Project[].class));
        List<Task> tasks = Arrays.asList(objectMapper.readValue(tasksFile, Task[].class));

        userService.save(users);
        projectService.save(projects);
        taskService.save(tasks);
    }
}
