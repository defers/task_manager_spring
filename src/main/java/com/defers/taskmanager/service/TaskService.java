package com.defers.taskmanager.service;

import com.defers.taskmanager.dto.TaskDTO;
import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.entity.User;
import com.defers.taskmanager.repository.TaskRepository;
import com.defers.taskmanager.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(isolation = Isolation.READ_COMMITTED)
public class TaskService implements ITaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private ApplicationUserDetailsService userService;
    @Autowired
    private ProjectService projectService;

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

        } catch (EntityNotFoundException e) {
            String errorMessage = String.format("Task with id: %s not found", id);
            throw new EntityNotFoundException(errorMessage);
        }
    }

    @Override
    public Task save(Task task) {
        Task taskObject = taskRepository.save(task);

        return taskObject;
    }

    @Override
    public List<TaskDTO> findAllTasksDTO() {

        List<Task> tasks = this.findAll();

        List<TaskDTO> tasksDTO = tasks
                .stream()
                .map(task -> this.convertFromTaskToDTO(task))
                .collect(Collectors.toList());

        return tasksDTO;
    }

    @Override
    public TaskDTO saveFromDTO(TaskDTO taskDTO) {

        Task task = this.convertFromDTOToTask(taskDTO);
        TaskDTO taskDTOObject = this.convertFromTaskToDTO(this.save(task));

        return taskDTOObject;
    }

    @Override
    public TaskDTO convertFromTaskToDTO(Task task) {

        if (task == null) {
            throw new RuntimeException("Can't convert NULL to TaskDTO");
        }

        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(task.getId());
        taskDTO.setDescription(task.getDescription());
        taskDTO.setDate(task.getDate());

        User user = task.getUser();
        if (user != null) {
            taskDTO.setUserId(user.getId());
        }

        Project project = task.getProject();
        if (project != null) {
            taskDTO.setProjectId(project.getId());
            taskDTO.setProjectName(project.getName());
        }

        return taskDTO;

    }

    @Override
    public Task convertFromDTOToTask(TaskDTO taskDTO) {

        if (taskDTO == null) {
            throw new RuntimeException("Can't convert NULL to Task");
        }

        Task task = new Task();
        task.setId(taskDTO.getId());
        task.setDescription(taskDTO.getDescription());
        task.setDate(taskDTO.getDate());

        Long userId = taskDTO.getUserId();
        if (userId != null) {
            User user = userService.findById(userId);
            task.setUser(user);
        }

        Long projectId = taskDTO.getProjectId();
        if (projectId != null) {
            Project project = projectService.findById(projectId);
            task.setProject(project);
        }

        return task;
    }

    @Override
    public TaskDTO findTaskDTOById(Long id) {
        Task task = this.findById(id);
        TaskDTO taskDTOObject = this.convertFromTaskToDTO(task);

        return taskDTOObject;
    }
}
