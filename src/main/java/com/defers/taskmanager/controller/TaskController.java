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
    public ResponseEntity<Task> getTask(@PathVariable(name="id") @Valid Long id,
                                        BindingResult bindingResult) throws Throwable {

        Task task = taskService.findById(id);

        return new ResponseEntity<Task>(task, HttpStatus.OK);

    }


}
