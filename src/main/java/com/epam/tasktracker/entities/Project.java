package com.epam.tasktracker.entities;

import com.epam.tasktracker.entities.builders.ProjectBuilder;
import com.epam.tasktracker.entities.embeddables.EmbCreatedAndUpdatedFields;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;

@Entity
@Table(name = "projects")
@Data
@NoArgsConstructor
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "closed")
    private boolean isClosed;

    @OneToMany(mappedBy = "project")
    private Collection<User> users;

    @Embedded
    private EmbCreatedAndUpdatedFields createdAtAndUpdatedAt = new EmbCreatedAndUpdatedFields();

    @Override
    public String toString() {
        return String.format("Project: id = %d, title = %s, description = %s, isClosed = %s",
                id, title, description, isClosed);
    }

    public Project(String title, String description, boolean isClosed) {
        this.title = title;
        this.description = description;
        this.isClosed = isClosed;
    }

    public ProjectBuilder builder() {
        return new ProjectBuilder();
    }
}
