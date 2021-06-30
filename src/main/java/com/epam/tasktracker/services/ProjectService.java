package com.epam.tasktracker.services;

import com.epam.tasktracker.entities.Project;
import com.epam.tasktracker.entities.User;
import com.epam.tasktracker.repositories.ProjectRepository;
import com.epam.tasktracker.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    @Autowired
    public ProjectService(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    private Project createProject(String title, String description) {
        return new Project().builder()
                .setTitle(title)
                .setDescription(description)
                .build();
    }

    public void save(String title, String description) {
        Project project = createProject(title, description);
        projectRepository.save(project);
    }

    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    public Optional<Project> findById(Long id) {
        return projectRepository.findById(id);
    }

    public void deleteById(Long id) {
        projectRepository.deleteById(id);
    }

    public Collection<User> getAllUsersFromProject(Long projectId) {
        return userRepository.findAllByProjectId(projectId);
    }

    @Transactional
    public void addUserForProject(Long userId, Long projectId) {
        Optional<User> user = userRepository.findById(userId);
        Optional<Project> project = projectRepository.findById(projectId);
        if (user.isPresent() && project.isPresent()) {
            project.get().getUsers().add(user.get());
            user.get().setProject(project.get());
            userRepository.save(user.get());
            projectRepository.save(project.get());
        } else {
            System.out.println("Unknown User or Project"); //todo
        }
    }
}
