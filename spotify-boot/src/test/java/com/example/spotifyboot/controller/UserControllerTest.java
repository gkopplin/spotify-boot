package com.example.spotifyboot.controller;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private List<User> users = new ArrayList<User>();

    @MockBean
    UserService userService;

    @MockBean
    private JwtUtil jwtUtil;

    @Autowired
    private MockMvc mockMvc;

    User user = new User();
    UserRole adminRole = new UserRole();

    @Before
    public void init() {

        adminRole.setName("ROLE_ADMIN");

        user.setUsername("George");
        user.setPassword("Clooney");
        user.setUserRole(adminRole);
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
                .andExpect(content().json("[{\"id\":null,\"username\":\"George\",\"password\":\"Clooney\",\"songs\":null,\"userRole\":{\"id\":null,\"name\":\"ROLE_ADMIN\"}}]"))
                .andReturn();
    }

    @Test
    public void login_Success() throws Exception{

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserInJson("joe","abc", adminRole.getName()));

        when(userService.login(any())).thenReturn("123456");

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"token\":\"123456\"}"))
                .andReturn();

    }

    private static String createUserInJson (String name, String password, String roleName) {
        return "{ \"username\": \"" + name + "\", " +
                "\"password\":\"" + password + "\", " +
                "\"userRole\": { \"name\": \"" + roleName +"\" }}";
    }
}

