package com.epam.tasktracker.commands;

import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

@Component
public class DeleteCommand extends Command {

    @Autowired
    public DeleteCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public boolean deleteEntity(String[] values) {
        Long id = parseLong(values[1]);
        if (id == null || id <= 0) {
            return false;
        }

        boolean done;
        if (values[0].equals("1")) {
            done = deleteUser(id);
        } else if (values[0].equals("2")) {
            done = deleteProject(id);
        } else if (values[0].equals("3")) {
            done = deleteTask(id);
        } else {
            return false;
        }

        return done;
    }

    private boolean deleteUser(Long id) {
        try {
            getUserService().deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    private boolean deleteProject(Long id) {
        try {
            getProjectService().deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }

    private boolean deleteTask(Long id) {
        try {
            getTaskService().deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            return false;
        }
        return true;
    }
}
