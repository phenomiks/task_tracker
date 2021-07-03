package com.epam.tasktracker.commands;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class TimeCommand extends Command {

    @Autowired
    public TimeCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public Long time(String strTaskId) {
        Long taskId = parseLong(strTaskId);
        if (taskId == null) {
            return null;
        }

        return time(taskId);
    }

    public Long time(Long taskId) {
        if (taskId <= 0) {
            return null;
        }

        Optional<Task> task = getTaskService().findById(taskId);
        if (task.isPresent()) {
            return task.get().getRemainingTime();
        }

        return null;
    }
}
