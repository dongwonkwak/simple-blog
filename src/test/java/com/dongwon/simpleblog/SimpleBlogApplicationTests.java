package com.dongwon.simpleblog;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.repository.PostRepository;
import com.dongwon.simpleblog.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class SimpleBlogApplicationTests {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;

    @LocalServerPort
    private int port;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserService userService;

    @BeforeEach
    public void setup() {
        postRepository.deleteAll();
    }

    @AfterEach
    public void cleanup() {
        postRepository.deleteAll();
    }

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

    @Test
    public void EditPost() throws Exception {
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

        Post saved = postRepository.save(post);
        Long savedId = saved.getId();

        //String url = "http://localhost:" + port + "/editPost/" + savedId;
        String expectedTitle = "title2";
        String expectedContent = "content2";
        post.setTitle(expectedTitle);
        post.setBody(expectedContent);



        /*var mockRequest = MockMvcRequestBuilders.post("/editPost/" + savedId)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(post));
        mockMvc.perform(mockRequest)
                .andExpect(status().isOk());*/

    }

}
