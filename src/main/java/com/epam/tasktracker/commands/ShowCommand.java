package com.epam.tasktracker.commands;

import com.epam.tasktracker.entities.Project;
import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.entities.User;
import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
public class ShowCommand extends Command {

    @Autowired
    public ShowCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public Collection<?> getAll(String value) {
        Long id = parseLong(value);
        if (id == null) {
            return null;
        }

        return getAll(id);
    }

    public Collection<?> getAll(long id) {
        if (id <= 0) {
            return null;
        }

        if (id == 1) {
            return getAllUsers(id);
        } else if (id == 2) {
            return getAllProjects(id);
        } else if (id == 3) {
            return getAllTasks(id);
        }
        return null;
    }

    private Collection<User> getAllUsers(Long id) {
        return getUserService().findAll();
    }

    private Collection<Project> getAllProjects(Long id) {
        return getProjectService().findAll();
    }

    private Collection<Task> getAllTasks(Long id) {
        return getTaskService().findAll();
    }
}
