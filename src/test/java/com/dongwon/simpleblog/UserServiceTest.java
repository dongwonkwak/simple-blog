package com.dongwon.simpleblog;

import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.repository.AuthorityRepository;
import com.dongwon.simpleblog.repository.UserRepository;
import com.dongwon.simpleblog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    AuthorityRepository authorityRepository;

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        this.userService = new UserService(userRepository, passwordEncoder, authorityRepository);
        user1 = User.builder()
                .username("homer")
                .email("homer@springfield.com")
                .password("password")
                .build();
        user2 = User.builder()
                .username("bart")
                .email("bart@springfield.com")
                .password("password")
                .build();
    }

    @Test
    void saveUserShouldWork() {
        // when
        when(passwordEncoder.encode(any(String.class)))
                .thenReturn(user1.getPassword());
        when(userRepository.save(any(User.class)))
                .thenReturn(user1);
        // given
        User user = userService.save(
                new UserDto(user1.getUsername(), user1.getEmail(), user1.getPassword()));
        // then
        assertThat(user.getUsername()).isEqualTo(user1.getUsername());
        assertThat(user.getEmail()).isEqualTo(user1.getEmail());
        assertThat(user.getPassword()).isEqualTo(user1.getPassword());
    }
}
