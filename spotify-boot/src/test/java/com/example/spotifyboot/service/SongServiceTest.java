package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.reposistory.SongRepository;
import org.junit.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class SongServiceTest {

    @Autowired
    SongService songService;

    @Mock
    SongRepository songRepository;

    @Test
    public void createSong_Song_Success() {
        Song song = new Song();
        song.setTitle("Another One Bites The Dust");
        song.setLength(3l);
        when(songRepository.save(any())).thenReturn(song);

        Song newSong = songService.createSong(song);

        assertEquals(song.getTitle(), newSong.getTitle());
        assertEquals(song.getLength(), newSong.getLength());

    }
}
