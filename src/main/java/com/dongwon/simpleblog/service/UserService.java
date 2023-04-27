package com.dongwon.simpleblog.service;

import com.dongwon.simpleblog.model.User;
import com.dongwon.simpleblog.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByUsername(email);
    }

    public User save(User user) {
        return userRepository.save(user);
    }
}
