package com.defers.taskmanager.service;

import com.defers.taskmanager.entity.User;
import com.defers.taskmanager.dto.ApplicationUser;
import com.defers.taskmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Slf4j
@Service
public class ApplicationUserDetailsService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByUsername(username);
        if (user == null) {
            log.error(String.format("User with username: %s not found", username));
            throw new UsernameNotFoundException(username);
        }

        return new ApplicationUser(user);
    }

    @Override
    public User findById(Long id) throws EntityNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(() -> {
                    log.error(String.format("User with id: %s not found", id));
                    return new EntityNotFoundException(
                            String.format("User with id: %s not found", id));
                });

        return user;
    }

    @Override
    public User findByUsername(String username) {
        User user = userRepository.findByUsername(username);

        return user;
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User saveOrUpdate(Long id, User user) throws EntityNotFoundException {

        User userObject = null;

        if (id == null) {

            userObject = userRepository.findByUsername(user.getUsername());

            if (userObject != null) {
                throw new EntityExistsException(String.format("User with username: %s is already exists",
                        user.getUsername()));
            }

            userObject = userRepository.save(user);
        }
        else {
            try {
                userObject = findById(id);
                userObject.setPassword(user.getPassword());
                userObject.setUsername(user.getUsername());
                userObject.setRoles(user.getRoles());

                userRepository.save(userObject);

            } catch (EntityNotFoundException e) {
                throw new EntityNotFoundException(
                        String.format("User with id: %s not found", id));
            }
        }

        return userObject;

    }

    @Override
    public void delete(Long id) throws EntityNotFoundException {
        User user = findById(id);

        if (user != null) {
            userRepository.delete(user);
        }

    }
}

