package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserRoleController {
    @Autowired
    UserRoleService userRoleService;

    @PostMapping("/role")
    public UserRole createUserRole(@RequestBody UserRole userRole) {
        return userRoleService.createUserRole(userRole);
    }

    @GetMapping("/role/{roleName}")
    public UserRole getUserRole(@PathVariable String roleName){
        return userRoleService.getUserRole(roleName);
    }
}
