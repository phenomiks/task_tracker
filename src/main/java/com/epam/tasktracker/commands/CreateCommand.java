package com.epam.tasktracker.commands;

import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateCommand extends Command {

    @Autowired
    public CreateCommand(UserService userService, ProjectService projectService, TaskService taskService) {
        super(userService, projectService, taskService);
    }

    public boolean createEntity(String[] values) {
        if (values[0].equals("1") && values.length == 4) {
            createNewUser(values);
        } else if (values[0].equals("2") && values.length == 3) {
            createNewProject(values);
        } else if (values[0].equals("3") && values.length == 3) {
            createNewTask(values);
        } else if (values[0].equals("4") && values.length == 4) {
            createNewSubtask(values);
        } else {
            return false;
        }
        return true;
    }

    private void createNewUser(String[] values) {
        String firstname = values[1];
        String lastname = values[2];
        String phone = values[3];
        getUserService().save(firstname, lastname, phone);
    }

    private void createNewProject(String[] values) {
        String title = values[1];
        String description = values[2];
        getProjectService().save(title, description);
    }

    private void createNewTask(String[] values) {
        String title = values[1];
        String description = values[2];
        getTaskService().save(title, description);
    }

    private void createNewSubtask(String[] values) {
        String title = values[1];
        String description = values[2];
        Long parentTaskId = parseLong(values[3]);
        getTaskService().save(title, description, parentTaskId);
    }
}
