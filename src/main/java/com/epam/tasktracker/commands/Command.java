package com.epam.tasktracker.commands;

import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;

public abstract class Command {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    public Command(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    protected UserService getUserService() {
        return userService;
    }

    protected ProjectService getProjectService() {
        return projectService;
    }

    protected TaskService getTaskService() {
        return taskService;
    }

    protected Long parseLong(String value) {
        long id;
        try {
            id = Long.parseLong(value);
        } catch (NumberFormatException e) {
            return null;
        }

        return id;
    }
}
