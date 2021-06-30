package com.epam.tasktracker.entities;

import com.epam.tasktracker.entities.builders.UserBuilder;
import com.epam.tasktracker.entities.embeddables.EmbCreatedAndUpdatedFields;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Collection;

@Entity
@Table(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, updatable = false, unique = true)
    private Long id;

    @Column(name = "firstname", length = 50, nullable = false)
    private String firstname;

    @Column(name = "lastname", length = 50, nullable = false)
    private String lastname;

    @Column(name = "phone", length = 20, nullable = false)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @OneToMany(mappedBy = "user")
    private Collection<Task> tasks;

    @Embedded
    private EmbCreatedAndUpdatedFields createdAtAndUpdatedAt = new EmbCreatedAndUpdatedFields();

    @Override
    public String toString() {
        return String.format("User: id = %d, firstname = %s, lastname = %s, phone = %s",
                id, firstname, lastname, phone);
    }

    public User(String firstname, String lastname, String phone) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phone = phone;
    }

    public UserBuilder builder() {
        return new UserBuilder();
    }
}
