package com.defers.taskmanager.service;

import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    public List<Task> findAll() {

        List<Task> tasks = taskRepository.findAll();

        return tasks;
    }

    @Override
    public Task findById(Long id) throws EntityNotFoundException {

        Task task = taskRepository.findById(id)
                        .orElseThrow(() -> {
                            String errorMessage = String.format("Task with id: %s not found", id);
                            return new EntityNotFoundException(errorMessage);
                        });

        return task;
    }

    @Override
    public void deleteById(Long id) {

        try {
            Task task = findById(id);
            taskRepository.delete(task);

        }
        catch (EntityNotFoundException e) {
            String errorMessage = String.format("Task with id: %s not found", id);
            throw new EntityNotFoundException(errorMessage);
        }
    }

    @Override
    public Task save(Task task) {
        Task taskObject = taskRepository.save(task);

        return taskObject;
    }
}
