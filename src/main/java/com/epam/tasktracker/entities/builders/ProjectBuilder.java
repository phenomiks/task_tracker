package com.epam.tasktracker.entities.builders;

import com.epam.tasktracker.entities.Project;

public class ProjectBuilder {
    private String title;
    private String description;
    private boolean isClosed;

    public ProjectBuilder() {
    }

    public ProjectBuilder setTitle(String title) {
        this.title = title;
        return this;
    }

    public ProjectBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ProjectBuilder setClosed(boolean closed) {
        this.isClosed = closed;
        return this;
    }

    public Project build() {
        return new Project(title, description, isClosed);
    }
}
