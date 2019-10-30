package com.example.spotifyboot.service;

import com.example.spotifyboot.model.User;

public interface UserService {
    public Iterable<User> listUsers();
    public User signup(User user);
    public User login(User user);
    public void deleteUser(Long userId);
    public void updateUser(User user);
}
