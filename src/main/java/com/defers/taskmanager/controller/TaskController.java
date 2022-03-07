package com.defers.taskmanager.controller;

import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
@CrossOrigin(origins = "*")
@RequestMapping("api/v1/tasks")
public class TaskController {

    @Autowired
    TaskService taskService;

    @GetMapping()
    public ResponseEntity<List<Task>> getAllTasks() {

        List<Task> tasks = taskService.findAll();

        return new ResponseEntity<List<Task>>(tasks, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Task> getTask(@PathVariable(name = "id") @Valid Long id) throws Throwable {

        Task task = taskService.findById(id);

        return new ResponseEntity<Task>(task, HttpStatus.OK);

    }

    @PostMapping("/save")
    public ResponseEntity<Task> saveTask(@RequestBody @Valid Task task) {

        Task taskObj = taskService.save(task);

        return new ResponseEntity<Task>(taskObj, HttpStatus.CREATED);

    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable(name = "id") Long id) {
        taskService.deleteById(id);

        return new ResponseEntity<String>(String.format("Task with id: %s has deleted", id), HttpStatus.OK);
    }

}
