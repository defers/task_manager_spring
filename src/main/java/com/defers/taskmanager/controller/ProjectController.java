package com.defers.taskmanager.controller;

import com.defers.taskmanager.dto.ProjectDTO;
import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/projects")
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    @GetMapping
    ResponseEntity<List<ProjectDTO>> getAllProjects() {
        List<ProjectDTO> projects = projectService.findAllProjectsDTO();

        return new ResponseEntity<>(projects, HttpStatus.OK);
    }

    @PostMapping("/save")
    ResponseEntity<ProjectDTO> saveProject(@RequestBody ProjectDTO project) {
        ProjectDTO projectObject = projectService.saveFromDTO(project);

        return new ResponseEntity<>(projectObject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Project> getProject(@PathVariable(name="id") Long id) {
        Project project = projectService.findById(id);

        return new ResponseEntity<>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProject(@PathVariable(name="id") Long id) {
        projectService.deleteById(id);
        return new ResponseEntity<>(String.format("Project with id: %s has deleted", id), HttpStatus.OK);
    }

}
