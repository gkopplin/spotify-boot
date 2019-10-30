package com.example.spotifyboot.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
public class Song {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String title;

    @Column
    private Long length;

    @JsonIgnore
    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(name ="user_song",
            joinColumns = {@JoinColumn(name = "song_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")})
    private List<User> users;

    public Song() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getLength() {
        return length;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setLength(Long length) {
        this.length = length;
    }
}
