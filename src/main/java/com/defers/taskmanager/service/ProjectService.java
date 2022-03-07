package com.defers.taskmanager.service;

import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.repository.ProjectRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
public class ProjectService implements IProjectService {

    @Autowired
    ProjectRepository projectRepository;

    @Override
    public Project findById(Long id) {
        Project project = projectRepository
                .findByIdNotDeleted(id)
                .orElseThrow(() -> {
                            String errorMessage = String.format("Project with id: %s not found", id);
                            return new EntityNotFoundException(errorMessage);
                });

        return project;
    }

    @Override
    public List<Project> findAll() {

        List<Project> projects = projectRepository
                .findAllNotDeleted().get();

        return projects;
    }

    @Override
    public void deleteById(Long id) {
        try{
            Project project = this.findById(id);
            project.setDeleted(true);
            this.save(project);
        }
        catch (EntityNotFoundException e) {
            String errorMessage = String.format("Project with id: %s not found", id);
            throw new EntityNotFoundException(errorMessage);
        }

    }

    @Override
    public Project save(Project project) {
        Project projectObject = projectRepository.save(project);

        return projectObject;
    }
}
