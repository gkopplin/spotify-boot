package com.example.spotifyboot.service;

import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.repository.UserRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    UserRoleRepository userRoleRepository;

    @Override
    public UserRole createUserRole(UserRole userRole) {
        return userRoleRepository.save(userRole);
    }

    @Override
    public UserRole getUserRole(String roleName) {
        return userRoleRepository.findByName(roleName.toUpperCase());
    }
}
