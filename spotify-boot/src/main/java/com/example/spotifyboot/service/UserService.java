package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface UserService extends UserDetailsService {
    public Iterable<User> listUsers();
    public User signup(User user);
    public String login(User user);
    public void deleteUser(Long userId);
    public void updateUser(User user);
    public List<Song> addSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException;
    public List<Song> removeSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException;
    public List<Song> getSongs(Long userId) throws ChangeSetPersister.NotFoundException;
}
