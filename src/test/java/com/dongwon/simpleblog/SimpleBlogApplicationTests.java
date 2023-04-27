package com.dongwon.simpleblog;

import com.dongwon.simpleblog.model.Post;
import com.dongwon.simpleblog.model.User;
import com.dongwon.simpleblog.repository.PostRepository;
import com.dongwon.simpleblog.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class SimpleBlogApplicationTests {

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;


    @Test
    void contextLoads() {
    }

    @Test
    public void loadPost() {
        // given
        String title = "title";
        String content = "content";
        String name = "homer";
        String email = "homer@springfield.com";
        String password = "password";

        User user = new User();
        user.setUsername(name);
        user.setEmail(email);
        user.setPassword(password);

        userService.save(user);

        Post post = new Post();
        post.setTitle(title);
        post.setBody(content);
        post.setUser(user);

        postRepository.save(post);

        // when
        List<Post> posts = postRepository.findAll();

        //then
        var p = posts.get(0);
        assertThat(p.getTitle()).isEqualTo(title);
        assertThat(p.getBody()).isEqualTo(content);
    }

}
