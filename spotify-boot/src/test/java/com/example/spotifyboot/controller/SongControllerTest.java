package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.service.SongService;
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

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(SongController.class)
public class SongControllerTest {
    private Song song = new Song();

    @Autowired
    MockMvc mockMvc;

    @MockBean
    SongService songService;

    @MockBean
    UserService userService;

    @MockBean
    JwtUtil jwtUtil;

    @Before
    public void initSong(){
        song.setId(1l);
        song.setTitle("Bohemian Rhapsody");
        song.setLength(6l);
    }

    @Test
    @WithMockUser(username = "batman", password = "bat", roles = {"ADMIN"})
    public void createSong_Song_Success() throws Exception {
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/song")
                .contentType(MediaType.APPLICATION_JSON)
                .content(createSongAsJson(song.getId(), song.getTitle(), song.getLength()));

        when(songService.createSong(any())).thenReturn(song);

        MvcResult result = mockMvc.perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(content().json(createSongAsJson(song.getId(), song.getTitle(), song.getLength())))
                .andReturn();

        assertNotNull(result);
    }
    private static String createSongAsJson (Long id, String title, Long length) {
        return "{ \"id\":\"" + id + "\", " +
               "\"title\":\"" + title + "\", " +
               "\"length\":\"" + length +"\"}";
    }
}
