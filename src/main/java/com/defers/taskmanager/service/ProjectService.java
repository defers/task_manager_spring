package com.defers.taskmanager.service;

import com.defers.taskmanager.dto.ProjectDTO;
import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.repository.ProjectRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public ProjectDTO convertFromProjectToDTO(Project project) {
        if (project == null) {
            throw new RuntimeException("Can't convert NULL to ProjectDTO");
        }

        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);

        return projectDTO;
    }

    @Override
    public Project convertFromDTOToProject(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            throw new RuntimeException("Can't convert NULL to Project");
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);

        return project;
    }

    @Override
    public ProjectDTO saveFromDTO(ProjectDTO projectDTO) {
        Project project = this.convertFromDTOToProject(projectDTO);
        ProjectDTO projectDTOObject = this.convertFromProjectToDTO(this.save(project));

        return projectDTOObject;
    }

    @Override
    public List<ProjectDTO> findAllProjectsDTO() {
        List<Project> projects = this.findAll();

        List<ProjectDTO> projectsDTO = projects
                .stream()
                .map(task -> this.convertFromProjectToDTO(task))
                .collect(Collectors.toList());

        return projectsDTO;
    }
}
