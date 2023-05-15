package com.dongwon.simpleblog;


import com.dongwon.simpleblog.controller.PostController;
import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;


import java.util.Optional;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
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
        /*var post = postService.create(
                PostDto.builder()
                        .title("title")
                        .body("body")
                        .build(),
                "user"
        );
        when(postService.findById(0L)).thenReturn(createPost());*/
        mvc.perform(
                        post("/posts")
                                .param("title", "title1")
                                .param("body", "body1")
                                .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/blog/user"));

        // when
        mvc.perform(get("/posts/0"))
                .andDo(print())
                .andExpect(status().isOk());
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

    @Test
    @WithMockUser
    void editPostShouldWork() throws Exception {
        // given
        when(postService.findById(0L)).thenReturn(createPost());

        // when
        //var postDto = postService
    }

    Optional<Post> createPost() {
        return Optional.ofNullable(postService.create(
                PostDto.builder()
                        .title("title")
                        .body("body")
                        .build(),
                "user"
        ));
    }
}
