package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import com.example.spotifyboot.reposistory.SongRepository;
import com.example.spotifyboot.reposistory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    SongRepository songRepository;

    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User signup(User user) {
        return userRepository.save(user);
    }

    @Override
    public User login(User user) {
        return userRepository.login(user.getUsername(), user.getPassword());
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<Song> addSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException {
        User fetchedUser = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Song fetchedSong = songRepository.findById(songId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        System.out.println(songId);
        System.out.println(fetchedSong.getId());
        fetchedUser.addSong(fetchedSong);
        userRepository.save(fetchedUser);

        return fetchedUser.getSongs();
    }
}
