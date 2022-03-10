package com.defers.taskmanager.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "projects")
@Data
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "deleted", nullable = false)
    private boolean deleted;

    @OneToMany(
            targetEntity=Task.class,
            mappedBy="project",
            cascade=CascadeType.ALL,
            fetch = FetchType.LAZY)
    private Set<Task> tasks;


}
