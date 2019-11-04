package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.reposistory.SongRepository;
import com.example.spotifyboot.reposistory.UserRepository;
import com.example.spotifyboot.reposistory.UserRoleRepository;
import com.example.spotifyboot.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    private List<Song> songs = new ArrayList<Song>();

    @Mock
    UserRepository userRepository;

    @Mock
    SongRepository songRepository;

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

    @InjectMocks
    private Song song;

    @Mock
    private List<GrantedAuthority> authorities;

    @Before
    public void initialize(){
        user.setUsername("george");
        user.setPassword("clooney");
        user.setId(1l);

        userRole.setName("ROLE_ADMIN");
        user.setUserRole(userRole);

        when(passwordEncoder.encode(any())).thenReturn("clooney");

        song.setId(1l);
        song.setTitle("Radio Ga Ga");
        song.setLength(5l);
        songs.add(song);
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
        when(userService.getUserByName(any())).thenReturn(user);
        when(userService.loadUserByUsername(any())).thenReturn(null);
        when(jwtUtil.generateToken(any())).thenReturn("12345");
        String token = userService.signup(user);

        assertEquals(token, "12345");
    }

    @Test
    public void login_User_Success() {
        when(userRepository.findByName(any())).thenReturn(user);
        when(passwordEncoder.matches(any(),any())).thenReturn(true);
        authorities = new ArrayList<GrantedAuthority>();
        when(userService.getUserByName(any())).thenReturn(user);
        when(userService.loadUserByUsername(any())).thenReturn(null);
        when(jwtUtil.generateToken(any())).thenReturn("12345");
        String token = userService.login(user);

        assertEquals(token, "12345");
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

    @Test
    @WithMockUser(roles={"ADMIN"})
    public void addSong_UserSongs_Success() throws ChangeSetPersister.NotFoundException {
        System.out.println(songs.size());
//        try {
            when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));
            when(songRepository.findById(anyLong())).thenReturn(java.util.Optional.of(song));
//        when(userRepository.save(any())).thenReturn(user.getSongs());
//            when(userRepository.save(any())).thenReturn(songs);
            List<Song> songList = userService.addSong(1l, 1l);

            System.out.println(songList);
            assertEquals(songs, songList);
//        } catch (ChangeSetPersister.NotFoundException e){
//            System.out.println(e.getMessage());
//            System.out.println("ChangeSetPersister error!!!");
//        }
    }

    @Test
    public void removeSong_UserSongs_Success() {

    }

    @Test
    public void getSongs_UserSongs_Success() throws ChangeSetPersister.NotFoundException {
        List<Song> foundSongs = null;

        when(userRepository.findById(anyLong())).thenReturn(java.util.Optional.of(user));

        foundSongs = userService.getSongs(1l);

        System.out.println(foundSongs.toString());
        assertEquals(songs, foundSongs);

    }
}
