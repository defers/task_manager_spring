package com.defers.taskmanager.service;

import com.defers.taskmanager.dto.ProjectDTO;
import com.defers.taskmanager.dto.UserDTO;
import com.defers.taskmanager.entity.Project;
import com.defers.taskmanager.entity.User;
import com.defers.taskmanager.dto.ApplicationUser;
import com.defers.taskmanager.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.stream.Collectors;

@Transactional(isolation = Isolation.READ_COMMITTED)
@Slf4j
@Service
public class ApplicationUserDetailsService implements UserDetailsService, IUserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    PasswordEncoder passwordEncoder;

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
                userObject.setEmail(user.getEmail());

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

    @Override
    public UserDTO convertFromUserToDTO(User user) {
        if (user == null) {
            throw new RuntimeException("Can't convert NULL to UserDTO");
        }

        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        userDTO.setNewPassword(false);
        userDTO.setPassword(null);

        return userDTO;
    }

    @Override
    public User convertFromDTOToUser(UserDTO userDTO) {
        if (userDTO == null) {
            throw new RuntimeException("Can't convert NULL to User");
        }

        User user = new User();

        String userName = userDTO.getUsername();
        if (userName != null) {
            User currentUser = this.findByUsername(userName);

            if (currentUser == null) {
                user.setUsername(userName);
                user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
            }
            else{
                user.setUsername(currentUser.getUsername());
                user.setId(currentUser.getId());
                if (userDTO.isNewPassword()) {
                    user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
                }
                else {
                    user.setPassword(currentUser.getPassword());
                }
            }
        }

        user.setEmail(userDTO.getEmail());
        user.setRoles(userDTO.getRoles());

        return user;
    }

    @Override
    public UserDTO saveFromDTO(UserDTO userDTO) {
        User user = this.convertFromDTOToUser(userDTO);
        UserDTO userDTOObject = this.convertFromUserToDTO(this.saveOrUpdate(user.getId(), user));

        return userDTOObject;
    }

    @Override
    public List<UserDTO> findAllUsersDTO() {
        List<User> users = this.findAll();

        List<UserDTO> usersDTO = users
                .stream()
                .map(user -> this.convertFromUserToDTO(user))
                .collect(Collectors.toList());

        return usersDTO;
    }
}

