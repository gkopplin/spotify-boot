package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;

import java.util.List;

public interface UserService {
    public Iterable<User> listUsers();
    public User signup(User user);
    public User login(User user);
    public void deleteUser(Long userId);
    public void updateUser(User user);
    public List<Song> addSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException;
}
