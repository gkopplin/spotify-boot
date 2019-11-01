package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.User;
import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.service.UserService;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
//@RunWith(MockitoJUnitRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {
    private List<User> users = new ArrayList<User>();

    @Mock
    UserService userService;

    @InjectMocks
    UserController userController;

    @InjectMocks
    private MockMvc mockMvc;

    @InjectMocks
    User user;

    @Before
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();

        UserRole adminRole = new UserRole();
        adminRole.setName("ROLE_ADMIN");

        user.setUsername("George");
        user.setPassword("Clooney");
        user.setUserRole(adminRole);
    }

    @Test
    public void listUsers_Users_Success() throws Exception {
//        users.add(user);
//        RequestBuilder requestBuilder = MockMvcRequestBuilders
//                .get("/user/list");
//
//        when(userService.listUsers()).thenReturn(users);
//
//        MvcResult result = mockMvc.perform(requestBuilder)
//                .andExpect(status().isOk())
//                .andExpect(content().json())
//                .andReturn();
//
//        assertEquals(users, result.get)
//        System.out.println(result.getResponse().getContentAsString());
    }
}
