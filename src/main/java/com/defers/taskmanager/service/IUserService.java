package com.defers.taskmanager.service;

import com.defers.taskmanager.entity.Task;
import com.defers.taskmanager.entity.User;

import javax.persistence.EntityNotFoundException;
import java.util.List;

public interface IUserService {

    User findById(Long id) throws EntityNotFoundException;

    List<User> findAll();

    User saveOrUpdate(Long id, User user) throws EntityNotFoundException;

    void delete(Long id) throws EntityNotFoundException;

}
