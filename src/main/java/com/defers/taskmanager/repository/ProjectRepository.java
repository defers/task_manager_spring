package com.defers.taskmanager.repository;

import com.defers.taskmanager.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {

    @Query("FROM Project AS p WHERE p.deleted = FALSE")
    Optional<List<Project>> findAllNotDeleted();

    @Query("FROM Project AS p WHERE p.deleted = FALSE AND p.id = ?1")
    Optional<Project> findByIdNotDeleted(Long id);

}
