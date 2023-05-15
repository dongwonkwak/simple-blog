package com.dongwon.simpleblog;

import static org.assertj.core.api.Assertions.*;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import org.junit.jupiter.api.Test;
class DomainTest {

    @Test
    void newUserShouldHaveNullId() {
        User user = User.builder()
                .username("alice")
                .email("alice@wonderland.com")
                .password("password")
                .build();
        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo("alice");
        assertThat(user.getEmail()).isEqualTo("alice@wonderland.com");
        assertThat(user.getPassword()).isEqualTo("password");
    }

    @Test
    void userSetterShouldMutateState() {
        User user = User.builder()
                .username("alice")
                .email("alice@wonderland.com")
                .password("password")
                .build();
        user.setUsername("bob");
        user.setEmail("bob@wonderland.com");
        user.setPassword("password1");

        assertThat(user.getId()).isNull();
        assertThat(user.getUsername()).isEqualTo("bob");
        assertThat(user.getEmail()).isEqualTo("bob@wonderland.com");
        assertThat(user.getPassword()).isEqualTo("password1");
    }

    @Test
    void newPostShouldHaveNullId() {
        Post post = Post.builder()
                .title("title")
                .body("body")
                .build();
        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("title");
        assertThat(post.getBody()).isEqualTo("body");
    }

    @Test
    void postSetterShouldMutateState() {
        Post post = Post.builder()
                .title("title")
                .body("body")
                .build();
        post.setTitle("title1");
        post.setBody("body1");

        assertThat(post.getId()).isNull();
        assertThat(post.getTitle()).isEqualTo("title1");
        assertThat(post.getBody()).isEqualTo("body1");
    }
}
