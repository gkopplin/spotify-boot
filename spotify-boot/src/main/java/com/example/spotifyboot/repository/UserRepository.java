package com.example.spotifyboot.repository;

import com.example.spotifyboot.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM User u WHERE u.username= ?1")
    public User findByName(String username);
}
