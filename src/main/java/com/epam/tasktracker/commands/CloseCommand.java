package com.epam.tasktracker.commands;

import com.epam.tasktracker.entities.Task;
import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CloseCommand extends Command {

    @Autowired
    public CloseCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public boolean close(String[] values) {
        Long id = parseLong(values[1]);
        if (id == null || id <= 0) {
            return false;
        }

        if (values[0].equals("3")) {
            return closeTask(id);
        }

        return false;
    }

    private boolean closeTask(Long id) {
        Optional<Task> task = getTaskService().findById(id);
        if (task.isPresent()) {
            recursiveCloseTask(task.get());
            return true;
        }
        return false;
    }

    private void recursiveCloseTask(Task task) {
        task.setClosed(true);
        getTaskService().save(task);
        for (Task t : task.getSubtasks()) {
            recursiveCloseTask(t);
        }
    }
}
