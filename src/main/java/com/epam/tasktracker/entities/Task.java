package com.epam.tasktracker.entities;

import com.epam.tasktracker.entities.builders.TaskBuilder;
import com.epam.tasktracker.entities.embeddables.EmbCreatedAndUpdatedFields;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "tasks")
@Data
@NoArgsConstructor
public class Task {
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

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Transient
    @Column(name = "parent_id")
    private Long parentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="parent_id")
    private Task parentTask;

    @OneToMany(mappedBy="parentTask", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Task> subtasks = new ArrayList<>();

    @Embedded
    private EmbCreatedAndUpdatedFields createdAtAndUpdatedAt = new EmbCreatedAndUpdatedFields();

    @Override
    public String toString() {
        Long ss = Objects.requireNonNullElse(parentId, -1L);

        return String.format("Task: id = %d, title = %s, description = %s, isClosed = %s, pa = %d",
                id, title, description, isClosed, ss);
    }

    public Task(String title, String description, boolean isClosed) {
        this.title = title;
        this.description = description;
        this.isClosed = isClosed;
    }

    public TaskBuilder builder() {
        return new TaskBuilder();
    }
}
