package com.example.spotifyboot.controller;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.service.SongService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SongController {
    @Autowired
    SongService songService;

    @PostMapping("/song")
    public Song createSong(@RequestBody Song song) {
        return songService.createSong(song);
    }
}
