package com.defers.taskmanager.service;

import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.bind.Name;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {

        List<Task> tasks = taskRepository.findAll();

        return tasks;
    }

    @Override
    public Task findById(Long id) throws Throwable {

        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> {
                            String errorMessage = String.format("Task with id: %s not found", id);
                            return new EntityNotFoundException(errorMessage);
                        });

        return task;
    }
}
