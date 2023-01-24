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
//@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    @Autowired
    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

//    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Role> roles) {
//        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
//    }

    @Transactional
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        UserEntity user = userOpt.get();
//        GrantedAuthority authority = new SimpleGrantedAuthority(compUser.getAuthority());
        UserDetails userDetails = (UserDetails)new User(user.getUsername(),
                user.getPassword(), user.getAuthorities());
        return userDetails;

//        UserEntity user = userRepository.findByUserName(username);
//        return new User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }
}
