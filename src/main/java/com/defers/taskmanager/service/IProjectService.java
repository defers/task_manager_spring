package com.defers.taskmanager.service;

import com.defers.taskmanager.dto.ProjectDTO;
import com.defers.taskmanager.entity.Project;

import java.util.List;

public interface IProjectService {
    Project findById(Long id);

    List<Project> findAll() throws Exception;

    void deleteById(Long id);

    Project save(Project project);

    ProjectDTO convertFromProjectToDTO(Project project);

    Project convertFromDTOToProject(ProjectDTO projectDTO);

    ProjectDTO saveFromDTO(ProjectDTO projectDTO);

    List<ProjectDTO> findAllProjectsDTO();
}
