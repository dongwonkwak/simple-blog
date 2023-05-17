package com.dongwon.simpleblog;

import com.dongwon.simpleblog.controller.PostController;
import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PostController.class)
@MockBean(JpaMetamodelMappingContext.class)
class PostControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private UserService userService;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    void getPostShouldWork() throws Exception {
        // given
        var post = createPost();
        given(postService.findById(any())).willReturn(post);

        // when
        String html = mvc.perform(get("/posts/0"))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        // then
        assertThat(html).contains(
                "class=\"form-control input-lg\" name=\"title\" value=\"test title\"/>",
                "<textarea id=\"body\" class=\"form-control input-lg\" rows=\"10\" name=\"body\">test body</textarea>");
    }

    @Test
    @WithMockUser
    void createPostShouldWork() throws Exception {
        mvc.perform(
                post("/posts")
                        .param("title", "title1")
                        .param("body", "body1")
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/user"));
        verify(postService).create(
                PostDto.builder()
                        .title("title1")
                        .body("body1")
                        .build(),
                "user");
    }

    Optional<Post> createPost() {
        User user = User.builder()
                .username("user")
                .email("user@example.com")
                .password("password")
                .build();

        return Optional.ofNullable(Post.builder()
                .title("test title")
                .body("test body")
                .user(user)
                .build());
    }
}
