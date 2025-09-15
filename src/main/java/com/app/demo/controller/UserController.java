package com.app.demo.controller;

import com.app.demo.models.User;
import com.app.demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/all")
    public List<User> getAllUsers() {
        return userService.findAll();
    }

    @GetMapping("/{username}")
    public ResponseEntity<User> getUser(@PathVariable("username") String username) {
        User user = userService.findByUsername(username);
        return user != null ? ResponseEntity.ok(user): ResponseEntity.notFound().build();
    }
}
