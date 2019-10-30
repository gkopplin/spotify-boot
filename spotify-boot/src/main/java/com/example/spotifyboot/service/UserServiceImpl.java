package com.example.spotifyboot.service;

import com.example.spotifyboot.model.User;
import com.example.spotifyboot.reposistory.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

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
//        return userRepository.login(user.getUsername(), user.getPassword());
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
}
