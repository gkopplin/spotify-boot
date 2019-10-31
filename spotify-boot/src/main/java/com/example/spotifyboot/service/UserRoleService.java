package com.example.spotifyboot.service;

import com.example.spotifyboot.model.UserRole;

public interface UserRoleService {
    public UserRole createUserRole(UserRole userRole);
    public UserRole getUserRole(String roleName);
}
