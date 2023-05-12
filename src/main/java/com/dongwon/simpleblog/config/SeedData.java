package com.dongwon.simpleblog.config;

import com.dongwon.simpleblog.domain.Authority;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.repository.AuthorityRepository;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {
    private final PostService postService;
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<PostDto> posts = postService.getAll();
        if (posts.isEmpty()) {
            Authority authority = new Authority();
            authority.setName("ROLE_USER");
            authorityRepository.save(authority);

            User user1 = new User();
            User user2 = new User();
            user1.setUsername("homer");
            user1.setEmail("homer@springfield.com");
            user1.setPassword("password");

            user2.setUsername("bart");
            user2.setEmail("bart@springfield.com");
            user2.setPassword("password");

            userService.save(user1);
            userService.save(user2);

            PostDto post1 = new PostDto();
            post1.setTitle("잠재력");
            post1.setBody("끝이 어딜까. \n너의 잠재력");
            post1.setUser(user1);

            PostDto post2 = new PostDto();
            post2.setTitle("너");
            post2.setBody("너라는 단어가 참 특별한 거 같아.\n\n 그냥 평점했던 내 하루에\n갑작스레 나타난\n너라는 사람말이야\n\n 늘 기쁘고 행복하고 활발한\n사람일 줄만 알았어\n자세히 들여다보니\n생각보다 깊더라고 사람이\n\n매일 나한테 알람 같아\n시간이 되면 너에게서\n연락이 와있거든");
            post2.setUser(user2);
            postService.save(post1);
            postService.save(post2);
        }
    }
}
