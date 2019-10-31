package com.example.spotifyboot.reposistory;

import com.example.spotifyboot.model.UserRole;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends CrudRepository<UserRole, Long> {
    @Query("FROM UserRole u WHERE u.name = ?1")
    public UserRole findByName(String name);

}
