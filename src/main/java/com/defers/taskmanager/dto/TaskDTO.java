package com.defers.taskmanager.dto;

import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDTO {

    private Long id;

    private String description;

    private LocalDateTime date;

    private Long userId;

    private Long projectId;

    private String ProjectName;

}
