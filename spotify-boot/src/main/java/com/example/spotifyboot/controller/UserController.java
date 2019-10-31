package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import com.example.spotifyboot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @PutMapping("/user/{userId}/song/{songId}")
    public List<Song> addSong(@PathVariable Long userId, @PathVariable Long songId) throws ChangeSetPersister.NotFoundException {
        return userService.addSong(userId, songId);
    }

    @PutMapping("/user/{userId}/song/{songId}")
    public List<Song> removeSong(@PathVariable Long userId, @PathVariable Long songId) throws ChangeSetPersister.NotFoundException {
        return userService.removeSong(userId, songId);
    }

    @GetMapping("/user/{userId}/song")
    public List<Song> getSongs(@PathVariable Long userId) throws ChangeSetPersister.NotFoundException {
        return userService.getSongs(userId);
    }
}
