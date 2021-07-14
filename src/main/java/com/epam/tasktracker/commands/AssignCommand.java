package com.epam.tasktracker.commands;

import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AssignCommand extends Command {

    @Autowired
    public AssignCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public boolean assign(String[] values) {
        Long userId = parseLong(values[1]);
        Long projectOrTaskId = parseLong(values[2]);
        if (userId == null || projectOrTaskId == null || userId <= 0 || projectOrTaskId <= 0) {
            return false;
        }

        try {
            if (values[0].equals("1")) {
                assignUserOnProject(userId, projectOrTaskId);
            } else if (values[0].equals("2")) {
                assignTaskOnUser(userId, projectOrTaskId);
            } else {
                return false;
            }
        } catch (IllegalArgumentException e) {
            return false;
        }

        return true;
    }

    private void assignUserOnProject(long userId, long projectId) {
        getProjectService().addUserForProject(userId, projectId);
    }

    private void assignTaskOnUser(long userId, long taskId) {
        getUserService().addTaskForUser(userId, taskId);
    }
}
