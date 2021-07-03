package com.epam.tasktracker.entities;

import com.epam.tasktracker.entities.builders.TaskBuilder;
import com.epam.tasktracker.entities.embeddables.EmbCreatedAndUpdatedFields;
import lombok.*;

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

    @Column(name = "lead_time")
    private Long leadTime;

    @Column(name = "closed")
    private boolean isClosed;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "parent_id", insertable = false, updatable = false)
    private Long parentId;

    @ManyToOne
    @JoinColumn(name="parent_id")
    private Task parentTask;

    @OneToMany(mappedBy="parentTask", fetch = FetchType.EAGER, orphanRemoval=true)
    private List<Task> subtasks;

    @Embedded
    private EmbCreatedAndUpdatedFields createdAtAndUpdatedAt = new EmbCreatedAndUpdatedFields();

    @Transient
    @Getter(value = AccessLevel.NONE)
    @Setter(value = AccessLevel.NONE)
    private long remainingTime;

    public long getRemainingTime() {
        remainingTime = 0;
        recursiveSumOfRemainingTime(this);
        return remainingTime;
    }

    private void recursiveSumOfRemainingTime(Task task) {
        remainingTime += task.getLeadTime();
        for (Task t : task.getSubtasks()) {
            recursiveSumOfRemainingTime(t);
        }
    }

    @Override
    public String toString() {
        Long parentTaskId = Objects.requireNonNullElse(parentId, -1L);

        return String.format("Task: id = %d, title = %s, description = %s, leadTime = %s, isClosed = %s, parentTaskId = %d",
                id, title, description, leadTime, isClosed, parentTaskId);
    }

    public Task(String title, String description, Long leadTime, boolean isClosed) {
        this.title = title;
        this.description = description;
        this.leadTime = leadTime;
        this.isClosed = isClosed;
    }

    public TaskBuilder builder() {
        return new TaskBuilder();
    }
}
