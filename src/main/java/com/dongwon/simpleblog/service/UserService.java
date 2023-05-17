package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.domain.Authority;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.repository.AuthorityRepository;
import com.dongwon.simpleblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.HashSet;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthorityRepository authorityRepository;


    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User save(UserDto userDto) {
        var authorities = new HashSet<Authority>();
        authorityRepository.findById("ROLE_USER").ifPresent(authorities::add);
        User user = User.builder()
                .username(userDto.username())
                .email(userDto.email())
                .password(passwordEncoder.encode(userDto.password()))
                .authorities(authorities)
                .build();
        return userRepository.save(user);
    }

    public boolean existsByUsername(String username) {
        return (userRepository.findByUsername(username).isPresent());
    }

    public boolean existsByEmail(String email) {
        return (userRepository.findByEmail(email).isPresent());
    }
}
