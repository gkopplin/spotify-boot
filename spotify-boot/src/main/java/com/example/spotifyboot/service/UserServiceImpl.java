package com.example.spotifyboot.service;

import com.example.spotifyboot.model.Song;
import com.example.spotifyboot.model.User;
import com.example.spotifyboot.model.UserRole;
import com.example.spotifyboot.reposistory.SongRepository;
import com.example.spotifyboot.reposistory.UserRepository;
import com.example.spotifyboot.reposistory.UserRoleRepository;
import com.example.spotifyboot.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    UserRoleService userRoleService;

    @Autowired
    SongRepository songRepository;

    @Autowired
    JwtUtil jwtUtil;

    @Autowired
    @Qualifier("encoder")
    PasswordEncoder bCryptPasswordEncoder;

    @Override
    public Iterable<User> listUsers() {
        return userRepository.findAll();
    }

    @Override
    public User signup(User user) {
        UserRole userRole = userRoleService.getUserRole(user.getUserRole().getName());
        user.setUserRole(userRole);
        return userRepository.save(user);
    }

    @Override
    public String login(User user) {
        if (userRepository.login(user.getUsername(), user.getPassword()) != null) {
            UserDetails userDetails = loadUserByUsername(user.getUsername());
            return jwtUtil.generateToken(userDetails);
        }
        return null;
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }

    @Override
    public void updateUser(User user) {
        userRepository.save(user);
    }

    @Override
    public List<Song> addSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException {
        User fetchedUser = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Song fetchedSong = songRepository.findById(songId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        fetchedUser.addSong(fetchedSong);
        userRepository.save(fetchedUser);

        return fetchedUser.getSongs();
    }

    @Override
    public List<Song> removeSong(Long userId, Long songId) throws ChangeSetPersister.NotFoundException {
        User fetchedUser = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);
        Song fetchedSong = songRepository.findById(songId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        fetchedUser.removeSong(fetchedSong);
        userRepository.save(fetchedUser);

        return fetchedUser.getSongs();
    }

    @Override
    public List<Song> getSongs(Long userId) throws ChangeSetPersister.NotFoundException {
        User fetchedUser = userRepository.findById(userId).orElseThrow(ChangeSetPersister.NotFoundException::new);

        return fetchedUser.getSongs();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = getUserByName(username);

        if(user==null)
            throw new UsernameNotFoundException("User null");

        return new org.springframework.security.core.userdetails.User(user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()),
                true, true, true, true, getGrantedAuthorities(user));
    }

    private User getUserByName(String username) {
        return userRepository.findByName(username);
    }

    private List<GrantedAuthority> getGrantedAuthorities(User user){
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

        authorities.add(new SimpleGrantedAuthority(user.getUserRole().getName()));

        return authorities;
    }
}
