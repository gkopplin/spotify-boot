package com.example.spotifyboot.reposistory;

import com.example.spotifyboot.model.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface UserRepository extends CrudRepository<User, Long> {

    @Query("FROM User u WHERE u.username= ?1 AND u.password= ?2")
    public User login(String username, String password);

    @Query("FROM User u WHERE u.username= ?1")
    public User findByName(String username);
}
