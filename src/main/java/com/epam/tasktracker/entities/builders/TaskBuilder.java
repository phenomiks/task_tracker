package com.epam.tasktracker.entities.builders;

import com.epam.tasktracker.entities.Task;

public class TaskBuilder {
    private String title;
    private String description;
    private Long leadTime;
    private boolean isClosed;

    public TaskBuilder() {
    }

    public TaskBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public TaskBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TaskBuilder setLeadTime(Long leadTime) {
        this.leadTime = leadTime;
        return this;
    }

    public TaskBuilder setClosed(boolean closed) {
        this.isClosed = closed;
        return this;
    }

    public Task build() {
        return new Task(title, description, leadTime, isClosed);
    }
}
