package com.defers.taskmanager.controller;

import com.defers.taskmanager.entity.User;
import com.defers.taskmanager.service.ApplicationUserDetailsService;
import com.defers.taskmanager.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    IUserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {

        List<User> users = userService.findAll();

        return ResponseEntity.ok().body(users);

    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable(name = "id") Long id) {

        User user = userService.findById(id);

        return ResponseEntity.ok().body(user);

    }

    @PostMapping("/save")
    public ResponseEntity<User> saveUser(@RequestBody User user) {

        User newUser = userService.saveOrUpdate(null, user);

        return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {

        userService.delete(id);

        return new ResponseEntity<String>(String.format("User with id: %s has deleted", id), HttpStatus.OK);
    }


}
