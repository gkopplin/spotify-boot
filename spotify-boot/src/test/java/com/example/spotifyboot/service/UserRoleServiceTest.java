package com.example.spotifyboot.service;

import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.repository.UserRoleRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserRoleServiceTest {
    @Mock
    UserRoleRepository userRoleRepository;

    @InjectMocks
    UserRole userRole;

    @InjectMocks
    UserRoleServiceImpl userRoleService;

    @Before
    public void init() {
        userRole.setName("ROLE_ADMIN");
    }

    @Test
    public void createUserRole_UserRole_Success() {
        when(userRoleRepository.save(any())).thenReturn(userRole);
        UserRole result = userRoleService.createUserRole(userRole);

        assertEquals(userRole, result);
    }

    @Test
    public void getUserRole_UserRole_Success() {
        when(userRoleRepository.findByName(any())).thenReturn(userRole);
        UserRole result = userRoleService.getUserRole(userRole.getName());

        assertEquals(userRole, result);
    }
}
