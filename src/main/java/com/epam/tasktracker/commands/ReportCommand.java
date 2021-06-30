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
import java.util.Optional;

@Component
public class ReportCommand extends Command {

    @Autowired
    public ReportCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public Collection<Task> report(String[] values) {
        long userId;
        long projectId;
        try {
            userId = Long.parseLong(values[0]);
            projectId = Long.parseLong(values[1]);
        } catch (NumberFormatException e) {
            return null;
        }

        if (userId <= 0 || projectId <= 0) {
            return null;
        }

        Optional<Project> project = getProjectService().findById(projectId);
        if (project.isPresent()) {
            Collection<User> users = getProjectService().getAllUsersFromProject(projectId);
            Optional<User> user = users.stream()
                    .filter(u -> userId == u.getId())
                    .findFirst();

            if (user.isPresent()) {
                return getUserService().getAllTasksFromUser(userId);
            }
        }

        return null;
    }
}
