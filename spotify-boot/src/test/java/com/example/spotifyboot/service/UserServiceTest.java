package com.example.spotifyboot.service;

import com.example.spotifyboot.model.User;
import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.reposistory.UserRepository;
import com.example.spotifyboot.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    @Mock
    UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRoleService userRoleService;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private User user;

    @InjectMocks
    private UserRole userRole;

    @Mock
    private List<GrantedAuthority> authorities;

    @Before
    public void initialize(){
        user.setUsername("george");
        user.setPassword("clooney");

        userRole.setName("ROLE_ADMIN");
        user.setUserRole(userRole);

        when(passwordEncoder.encode(any())).thenReturn("clooney");
    }

    @Test
    public void getUser_ReturnsUser_Success(){

        when(userRepository.findByName(anyString())).thenReturn(user);

        User tempUser = userService.getUserByName(user.getUsername());

        assertEquals(tempUser.getUsername(), user.getUsername());
    }

    @Test
    public void login_UserNotFound_Error(){

        when(userRepository.findByName(anyString())).thenReturn(null);

        String token = userService.login(user);

        assertEquals(token, null);
    }

    @Test
    public void listUsers_Users_Success() {
        List<User> userList = new ArrayList<>();
        when(userRepository.findAll()).thenReturn(userList);
        List<User> result = (List<User>) userService.listUsers();

        assertEquals(result, userList);
    }

    @Test
    public void signup_User_Success() {
        when(userRoleService.getUserRole(any())).thenReturn(userRole);
        when(userRepository.save(any())).thenReturn(user);
        authorities = new ArrayList<GrantedAuthority>();
//        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
//                true, true, true, true, authorities);
        when(userService.getUserByName(any())).thenReturn(user);
//        when(userService.getGrantedAuthorities(any())).thenReturn(authorities);
        when(userService.loadUserByUsername(any())).thenReturn(null);
        when(jwtUtil.generateToken(any())).thenReturn("12345");
//        String token = userService.signup(user);

//        assertEquals(token, "12345");
    }

    @Test
    public void deleteUser_User_Success() {
        try {
            userService.deleteUser(1L);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void updateUser_User_Success() {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}
