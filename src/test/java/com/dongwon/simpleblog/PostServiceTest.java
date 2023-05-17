package com.dongwon.simpleblog;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.mapper.PostMapper;
import com.dongwon.simpleblog.repository.PostRepository;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PostServiceTest {
    PostService postService;

    @Mock
    PostRepository postRepository;
    @Mock
    UserService userService;

    @Spy
    PostMapper postMapper = Mappers.getMapper(PostMapper.class);

    User user1;
    User user2;

    @BeforeEach
    void setUp() {
        this.postService = new PostService(postRepository, postMapper, userService);

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
    void getAllShouldReturnAllItem() {
        // given
        Post post1 = Post.builder().title("title1").body("body1").user(user1)
                .build();
        Post post2 = Post.builder().title("title2").body("body2").user(user2)
                .build();
        when(postRepository.findAll()).thenReturn(List.of(post1, post2));
        // when
        List<Post> posts = postService.getAll();
        // then
        assertThat(posts).containsExactly(post1, post2);
    }

    @Test
    void creatingNewPostShouldReturnSameData() {
        // given
        given(postRepository.save(any(Post.class)))
                .willReturn(Post.builder().title("title").body("body").user(user1)
                        .build());
        when(userService.findByUsername(user1.getUsername()))
                .thenReturn(Optional.ofNullable(user1));
        // when
        Post post = postService.create(PostDto.builder()
                .title("title").body("body").build(), user1.getUsername());

        // then
        assertThat(post.getBody()).isEqualTo("body");
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getUser().getUsername()).isEqualTo(user1.getUsername());
    }

    @Test
    void findPostShouldReturnSameData() {
        // given
        given(postRepository.findById(any(Long.class)))
                .willReturn(Optional.ofNullable(Post.builder().title("title").body("body").user(user1)
                        .build()));
        // when
        Optional<Post> optionalPost = postService.findById(0L);
        assertThat(optionalPost).isPresent();
        Post post = optionalPost.get();
        // then
        assertThat(post.getBody()).isEqualTo("body");
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getUser().getUsername()).isEqualTo(user1.getUsername());
    }
}
