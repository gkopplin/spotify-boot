package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.service.UserRoleService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(UserRoleController.class)
public class UserRoleControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    UserRoleService userRoleService;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    private UserRole userRole = new UserRole();

    @Before
    public void init() {
        userRole.setName("ROLE_ADMIN");
    }

    private static String createUserRoleInJson (String name) {
        return "{ \"name\": \"" + name + "\"}";
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void createUserRole_UserRole_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/role")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createUserRoleInJson("ROLE_ADMIN"));

        when(userRoleService.createUserRole(any())).thenReturn(userRole);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":null,\"name\":\"ROLE_ADMIN\"}"))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void getUserRole_UserRole_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .get("/role/ROLE_ADMIN");

        when(userRoleService.getUserRole(any())).thenReturn(userRole);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json("{\"id\":null,\"name\":\"ROLE_ADMIN\"}"))
                .andReturn();
    }
}
