package com.dongwon.simpleblog.config;

import com.dongwon.simpleblog.domain.Authority;
import com.dongwon.simpleblog.domain.Post;

import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.repository.AuthorityRepository;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@RequiredArgsConstructor
public class SeedData implements CommandLineRunner {
    private final PostService postService;
    private final UserService userService;
    private final AuthorityRepository authorityRepository;

    @Override
    public void run(String... args) throws Exception {
        List<Post> posts = postService.getAll();
        if (posts.isEmpty()) {
            Authority authority = new Authority();
            authority.setName("ROLE_USER");
            authorityRepository.save(authority);

            UserDto user1 = new UserDto(
                    "homer",
                    "homer@springfield.com",
                    "password");
            UserDto user2 = new UserDto(
                    "bart",
                    "bart@springfield.com",
                    "password");
            userService.save(user1);
            userService.save(user2);

            PostDto post1 = PostDto.builder()
                            .title("잠재력")
                            .body("끝이 어딜까. \n너의 잠재력")
                            .build();

            PostDto post11 = PostDto.builder()
                            .title("123")
                            .body("fffff")
                            .build();

            PostDto post2 = PostDto.builder()
                            .title("너")
                            .body("너라는 단어가 참 특별한 거 같아.\n\n 그냥 평점했던 내 하루에\n갑작스레 나타난\n너라는 사람말이야\n\n 늘 기쁘고 행복하고 활발한\n사람일 줄만 알았어\n자세히 들여다보니\n생각보다 깊더라고 사람이\n\n매일 나한테 알람 같아\n시간이 되면 너에게서\n연락이 와있거든")
                            .build();

            postService.create(post1, user1.username());
            postService.create(post11, user1.username());
            postService.create(post2, user2.username());
        }
    }
}
