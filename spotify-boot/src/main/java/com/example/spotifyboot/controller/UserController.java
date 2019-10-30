package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.User;
import com.example.spotifyboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    public Iterable<User> listUsers(){
        return userService.listUsers();
    }

    @PostMapping("/signup")
    public User signup(@RequestBody User newUser){
        return userService.signup(newUser);
    }

    @PostMapping("/login")
    public User login(@RequestBody User newUser){
        return userService.login(newUser);
    }

    @DeleteMapping("/user/{userId}")
    public HttpStatus deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return HttpStatus.OK;
    }
}
