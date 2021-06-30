package com.epam.tasktracker.entities;

import com.epam.tasktracker.entities.builders.TaskBuilder;
import com.epam.tasktracker.entities.embeddables.EmbCreatedAndUpdatedFields;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    @Embedded
    private EmbCreatedAndUpdatedFields createdAtAndUpdatedAt = new EmbCreatedAndUpdatedFields();

    @Override
    public String toString() {
        return String.format("Project: id = %d, title = %s, description = %s, isClosed = %s",
                id, title, description, isClosed);
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
