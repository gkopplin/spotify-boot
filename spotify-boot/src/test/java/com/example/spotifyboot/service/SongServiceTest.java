package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.service.SongServiceImpl;
import com.example.spotifyboot.service.SongService;
import com.example.spotifyboot.repository.SongRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class SongServiceTest {
    @InjectMocks
    SongServiceImpl songService;

    @InjectMocks
    private Song song;

    @Mock
    SongRepository songRepository;

    @Test
    public void createSong_Song_Success() {
        song.setTitle("Another One Bites The Dust");
        song.setLength(3l);
        when(songRepository.save(any())).thenReturn(song);

        Song newSong = songService.createSong(song);

        assertEquals(song.getTitle(), newSong.getTitle());
        assertEquals(song.getLength(), newSong.getLength());

    }
}
