package com.fullstack.backend.controller;

import com.fullstack.backend.common.model.ListResponse;
import com.fullstack.backend.model.User;
import com.fullstack.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:8080", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {

    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/save")
    public User saveUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/update")
    public User updateUser(@RequestBody User user) {
        return userService.updateUser(user);
    }

    @DeleteMapping("/delete")
    public Boolean deleteUser(@RequestParam Long id) {
        return userService.deleteUserById(id);
    }

    @GetMapping("/get")
    public User getUserById(@RequestParam Long id) {
        return userService.getUserById(id);
    }

    @GetMapping("/list")
    public ListResponse listUsers() {
        return userService.listUsers();
    }

}
