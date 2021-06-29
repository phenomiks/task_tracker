package com.epam.tasktracker;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskTrackerSystemApplication implements CommandLineRunner {
    public static void main(String[] args) {
        SpringApplication.run(TaskTrackerSystemApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

    }
}
