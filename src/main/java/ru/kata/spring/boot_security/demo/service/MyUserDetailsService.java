package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ru.kata.spring.boot_security.demo.model.UserEntity;
import ru.kata.spring.boot_security.demo.repository.UserRepository;


import java.util.Optional;


// my own security

@Service
@Transactional
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserEntity user = userOpt.get();
        UserDetails userDetails = new User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
        return userDetails;

    }
}
