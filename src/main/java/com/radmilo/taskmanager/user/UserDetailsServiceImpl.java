package com.radmilo.taskmanager.user;

import com.radmilo.taskmanager.entity.User;
import com.radmilo.taskmanager.exception.UserEmailNotFoundException;
import com.radmilo.taskmanager.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        Optional<User> userDB = userRepository.findByEmail(email);
        if (userDB.isEmpty()) {
            throw new UserEmailNotFoundException("User with email " + email + " was not found in database");
        }
        User foundUser = userDB.get();

        Set<GrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority(foundUser.getRole().name()));

        return new org.springframework.security.core.userdetails.User(foundUser.getEmail(), foundUser.getPassword(), authorities);


    }
}
