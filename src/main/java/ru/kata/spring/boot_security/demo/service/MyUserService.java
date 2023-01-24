package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.UserEntity;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class MyUserService {
//public class MyUserService implements UserDetailsService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public MyUserService(UserRepository userRepository, PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    public List<UserEntity> allUsers() {
        return userRepository.findAll();
    }

    public void saveUser(UserEntity userEntity) {
        userEntity.setUserName(userEntity.getUsername());
        userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public void deleteUser(Long userId) {
            userRepository.deleteById(userId);
    }

    public UserEntity findByUserName(String userName) {
        Optional<UserEntity> userOpt = userRepository.findByUsername(userName);
        if (userOpt.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return userOpt.get();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).get();
    }

    public List<Role> getRoles() {
        return roleRepository.findAll();
    }

}


