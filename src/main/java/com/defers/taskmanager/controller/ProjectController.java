package com.defers.taskmanager.controller;

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
    ResponseEntity<List<Project>> getAllProjects() {
        List<Project> projects = projectService.findAll();

        return new ResponseEntity<List<Project>>(projects, HttpStatus.OK);
    }

    @PostMapping("/save")
    ResponseEntity<Project> saveProject(@RequestBody Project project) {
        Project projectObject = projectService.save(project);

        return new ResponseEntity<Project>(projectObject, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    ResponseEntity<Project> getProject(@PathVariable(name="id") Long id) {
        Project project = projectService.findById(id);

        return new ResponseEntity<Project>(project, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteProject(@PathVariable(name="id") Long id) {
        projectService.deleteById(id);
        return new ResponseEntity<String>(String.format("Project with id: %s has deleted", id), HttpStatus.OK);
    }

}
