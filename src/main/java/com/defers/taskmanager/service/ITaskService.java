package com.defers.taskmanager.service;

import com.defers.taskmanager.dto.TaskDTO;
import com.defers.taskmanager.entity.Task;

import java.util.List;

public interface ITaskService {

    Task findById(Long id) throws Throwable;

    List<Task> findAll();

    void deleteById(Long id);

    Task save(Task task);

    TaskDTO convertFromTaskToDTO(Task task);

    Task convertFromDTOToTask(TaskDTO taskDTO);

    TaskDTO saveFromDTO(TaskDTO taskDTO);

    List<TaskDTO> findAllTasksDTO();

    TaskDTO findTaskDTOById(Long id);
}
