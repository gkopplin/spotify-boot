package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.service.UserService;
import com.example.spotifyboot.util.JwtUtil;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private List<User> users = new ArrayList<User>();
    private List<Song> songs = new ArrayList<Song>();

    @MockBean
    UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    User user = new User();
    UserRole adminRole = new UserRole();
    Song song = new Song();

    @Before
    public void init() {

        adminRole.setName("ROLE_ADMIN");

        user.setUsername("george");
        user.setPassword("clooney");
        user.setUserRole(adminRole);

        song.setId(1l);
        song.setTitle("We Will Rock You");
        song.setLength(2l);
        songs.add(song);

        user.setSongs(songs);
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void listUsers_Users_Success() throws Exception {
        users.add(user);
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/list");

        when(userService.listUsers()).thenReturn(users);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[{\"id\":null,\"username\":\"george\",\"password\":\"clooney\",\"songs\":null,\"userRole\":{\"id\":null,\"name\":\"ROLE_ADMIN\"}}]"))
                .andReturn();
    }

    @Test
    public void login_User_Success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("george","clooney", adminRole.getName()));

        when(userService.login(any())).thenReturn("123456");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();
    }

    @Test
    public void signup_User_Success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/signup")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("george","clooney", adminRole.getName()));

        when(userService.signup(any())).thenReturn("123456");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void deleteUser_Success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("george","clooney", adminRole.getName()));

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

    @Test
    @WithMockUser(username = "george", password = "clooney", roles = {"ADMIN"})
    public void addSong_UserSongs_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .put("/user/1/song/1");

        when(userService.addSong(anyLong(),any())).thenReturn(user.getSongs());

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(content().json("[{\"id\":1,\"title\":\"We Will Rock You\",\"length\":2}]"))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "george", password = "clooney", roles = {"ADMIN"})
    public void removeSong_UserSongs_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .delete("/user/1/song/1");

        when(userService.removeSong(anyLong(), anyLong())).thenReturn(new ArrayList<Song>());

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("[]"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "george", password = "clooney", roles = {"ADMIN"})
    public void getSongs_User_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/user/1/song");

        when(userService.getSongs(anyLong())).thenReturn(user.getSongs());

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andReturn();

        System.out.println(result.getResponse().getContentAsString());

        assertNotNull(result);
    }

    private static String createUserInJson (String name, String password, String roleName) {
        return "{ \"username\": \"" + name + "\", " +
                "\"password\":\"" + password + "\", " +
                "\"userRole\": { \"name\": \"" + roleName +"\" }}";
    }
}

