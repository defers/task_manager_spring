package com.defers.taskmanager.configuration;

import com.defers.taskmanager.entity.User;
import com.defers.taskmanager.service.ApplicationUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class StartupConfiguration implements CommandLineRunner {

    @Autowired
    private final ApplicationUserDetailsService userService;

    public StartupConfiguration(ApplicationUserDetailsService userService) {
        this.userService = userService;
    }

    @Override
    public void run(String...args) throws Exception {

        int size = userService.findAll().size();
        User user = userService.findByUsername("admin");

        if (user == null && size == 0) {
            User userObject = new User();
            userObject.setUsername("admin");
            userObject.setPassword("$2a$10$rxvqjoiuyKZyGwc9uHVk0.XKkChQsAEL7avjhRLoFjL6pwJjxLcgG");
            userService.saveOrUpdate(null, userObject);
        }
    }
}
