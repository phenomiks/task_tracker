package com.epam.tasktracker;

import com.epam.tasktracker.commands.*;
import com.epam.tasktracker.entities.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Collection;

@Component
public class TaskTracker implements CommandLineRunner {
    private final CreateCommand createCommand;
    private final DeleteCommand deleteCommand;
    private final ShowCommand showCommand;
    private final AssignCommand assignCommand;
    private final ReportCommand reportCommand;

    private BufferedReader bf;

    @Autowired
    public TaskTracker(CreateCommand createCommand, DeleteCommand deleteCommand, ShowCommand showCommand, AssignCommand assignCommand, ReportCommand reportCommand) {
        this.createCommand = createCommand;
        this.deleteCommand = deleteCommand;
        this.showCommand = showCommand;
        this.assignCommand = assignCommand;
        this.reportCommand = reportCommand;
    }

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

    @Override
    public void run(String... args) {
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
            System.out.println("Enter the basic command. Enter 'exit' to terminate the application");
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
        System.out.println("Enter the command to create\n" +
                "- user: 1/firstname/lastname/phone\n" +
                "- project: 2/title/description\n" +
                "- task: 3/title/description");

        String command = bfReadLine();
        String[] values = command.split("/");

        boolean isDone = createCommand.createEntity(values);
        if (isDone) {
            System.out.println("done!");
        } else {
            System.out.println("Invalid command syntax");
        }
    }

    private void doDeleteCommand() {
        System.out.println("Enter the command to delete\n" +
                "- user: 1/id\n" +
                "- project: 2/id\n" +
                "- task: 3/id");

        String command = bfReadLine();
        String[] values = command.split("/");
        if (values.length != 2) {
            System.out.println("Invalid command syntax");
            return;
        }

        boolean isDone = deleteCommand.deleteEntity(values);
        if (isDone) {
            System.out.println("done!");
        } else {
            System.out.println("Invalid command syntax");
        }
    }

    private void doShowCommand() {
        System.out.println("Enter the specified number to show all\n" +
                "- users: 1\n" +
                "- projects: 2\n" +
                "- tasks: 3");

        String command = bfReadLine();

        Collection<?> collection = showCommand.getAll(command);
        if (collection == null) {
            System.out.println("Invalid command syntax");
        } else if (collection.isEmpty()) {
            System.out.println("Empty");
        } else {
            collection.forEach(System.out::println);
        }
    }

    private void doAssignCommand() {
        System.out.println("Enter the following command to assign:\n" +
                "- user to the project: 1/userId/projectId\n" +
                "- task to the user: 2/userId/taskId");

        String command = bfReadLine();
        String[] values = command.split("/");
        if (values.length != 3) {
            System.out.println("Invalid command syntax");
            return;
        }

        boolean isDone = assignCommand.assign(values);
        if (isDone) {
            System.out.println("done!");
        } else {
            System.out.println("Invalid command syntax");
        }
    }

    private void doReportCommand() {
        System.out.println("Enter the following command to generate the report\n" +
                "userId/projectId");

        String command = bfReadLine();
        String[] values = command.split("/");
        if (values.length != 2) {
            System.out.println("Invalid command syntax");
            return;
        }

        Collection<Task> collection = reportCommand.report(values);
        if (collection == null) {
            System.out.println("Invalid command syntax");
        } else if (collection.isEmpty()) {
            System.out.println("Empty");
        } else {
            collection.forEach(System.out::println);
        }
    }
}
