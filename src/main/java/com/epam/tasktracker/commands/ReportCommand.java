package com.epam.tasktracker.commands;

import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReportCommand extends Command {

    @Autowired
    public ReportCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }
}
