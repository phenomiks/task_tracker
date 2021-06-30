package com.epam.tasktracker;

import com.epam.tasktracker.entities.Project;
import com.epam.tasktracker.entities.User;
import com.epam.tasktracker.services.ProjectService;
import com.epam.tasktracker.services.TaskService;
import com.epam.tasktracker.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Component
public class TaskTracker implements CommandLineRunner {
    private final UserService userService;
    private final ProjectService projectService;
    private final TaskService taskService;

    private BufferedReader bf;

    @PostConstruct
    public void init() {
        bf = new BufferedReader(new InputStreamReader(System.in));
    }

    @PreDestroy
    public void destroy() {
        try {
            bf.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Autowired
    public TaskTracker(UserService userService, ProjectService projectService, TaskService taskService) {
        this.userService = userService;
        this.projectService = projectService;
        this.taskService = taskService;
    }

    @Override
    public void run(String... args) throws IOException {
        System.out.println("----------------------------------------\n" +
                "This is a console application for tracking tasks.\n" +
                "----------------------------------------\n" +
                "The following basic commands are supported:\n" +
                "CREATE - to create a new user/project/task;\n" +
                "DELETE - to delete user/project/task;\n" +
                "SHOW - to show a list of user/project/task;\n" +
                "ASSIGN - to bind a user to a project/task to a user;\n" +
                "REPORT - to generate the report of all tasks created for specified project by specified user;\n" +
                "EXIT - to exit the application.\n");

        while (true) {
            System.out.println("Enter the basic command");
            String command = bfReadLine();

            if (command.equalsIgnoreCase(ConsoleCommands.CREATE.name())) {
                doCreateCommand();
            } else if (command.equalsIgnoreCase(ConsoleCommands.SHOW.name())) {
                doShowCommand();
            } else if (command.equalsIgnoreCase(ConsoleCommands.DELETE.name())) {
                doDeleteCommand();
            } else if (command.equalsIgnoreCase(ConsoleCommands.ASSIGN.name())) {
                doAssignCommand();
            } else if (command.equalsIgnoreCase(ConsoleCommands.REPORT.name())) {
                doReportCommand();
            } else if (command.equalsIgnoreCase(ConsoleCommands.EXIT.name())) {
                break;
            } else {
                System.out.println("Command not recognized");
            }
        }
    }

    private String bfReadLine() {
        String line = "";
        try {
            line = bf.readLine().trim();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }

    private void doCreateCommand() {
        System.out.println("Введите команду в следующем виде для создания:\n" +
                "- пользователя: 1/firstname/lastname/phone\n" +
                "- проекта: 2/title/description\n" +
                "- задачи: 3/title/description");

        String command = bfReadLine();
        String[] values = command.split("/");

        if (values[0].equals("1") && values.length == 4) {
            String firstname = values[1];
            String lastname = values[2];
            String phone = values[3];
            userService.save(firstname, lastname, phone);
        } else if (values[0].equals("2") && values.length == 3) {
            String title = values[1];
            String description = values[2];
            projectService.save(title, description);
        } else if (values[0].equals("3") && values.length == 3) {
            String title = values[1];
            String description = values[2];
            taskService.save(title, description);
        } else {
            System.out.println("Invalid command syntax");
        }
    }

    private void doReportCommand() throws IOException {
        System.out.println("Для показа всех задач для определенного проекта и пользователя введите:\n" +
                "userId/projectId\n");
        String string = bf.readLine();
        String[] values = string.split("/");

        if (values.length != 2) {
            System.out.println("Неверный синтаксис команды");
            return;
        }

        Long userId = Long.parseLong(values[0]); //todo
        Long projectId = Long.parseLong(values[1]);
        Optional<Project> project = projectService.findById(projectId);
        if (project.isPresent()) {
            Collection<User> users = projectService.getAllUsersFromProject(projectId);
            User user = null;
            for (User u : users) {
                if (u.getId().equals(userId)) {
                    user = u;
                    break;
                }
            }
            if (user != null) {
                userService.getAllTasksFromUser(userId).forEach(System.out::println);
            }
        }
    }

    private void doAssignCommand() throws IOException {
        System.out.println("Для привязки пользователя к проекту выполните следующую команду:\n" +
                "1/userId/projectId\n" +
                "2/userId/taskId");
        String string = bf.readLine();
        String[] values = string.split("/");

        if (values.length != 3) {
            System.out.println("Неверный синтаксис команды");
            return;
        }

        Long userId = Long.parseLong(values[1]); //todo
        Long projectOrTaskId = Long.parseLong(values[2]);
        if (values[0].equals("1")) {
            projectService.addUserForProject(userId, projectOrTaskId);
        } else if (values[0].equals("2")) {
            userService.addTaskForUser(projectOrTaskId, userId);
        } else {
            System.out.println("Неверный синтаксис команды");
        }
    }

    private void doShowCommand() throws IOException {
        System.out.println("Введите указанное число для показа:\n" +
                "- пользователей: 1\n" +
                "- проектов: 2\n" +
                "- задач: 3");
        String string = bf.readLine();
        if (string.equalsIgnoreCase("1")) {
            userService.findAll().forEach(System.out::println);
        } else if (string.equalsIgnoreCase("2")) {
            List<Project> projectList = projectService.findAll();
            projectService.findAll().forEach(System.out::println);
        } else if (string.equalsIgnoreCase("3")) {
            taskService.findAll().forEach(System.out::println);
        } else {
            System.out.println("Неверный синтаксис команды");
        }
    }

    private void doDeleteCommand() throws IOException {
        System.out.println("Введите число, а также id для удаления:\n" +
                "- пользователей: 1/id\n" +
                "- проектов: 2/id\n" +
                "- задач: 3/id");
        String string = bf.readLine();
        String[] values = string.split("/");
        if (values.length != 2) {
            System.out.println("Неверный синтаксис команды");
            return;
        }

        Long id = Long.parseLong(values[1]);
        if (values[0].equals("1")) {
            userService.deleteById(id);
        } else if (values[0].equals("2")) {
            projectService.deleteById(id);
        } else if (values[0].equals("3")) {
            taskService.deleteById(id);
        } else {
            System.out.println("Неверный синтаксис команды");
        }
    }
}
