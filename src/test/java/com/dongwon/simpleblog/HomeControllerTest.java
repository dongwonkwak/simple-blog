package com.dongwon.simpleblog;


import com.dongwon.simpleblog.controller.HomeController;
import com.dongwon.simpleblog.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.assertj.core.api.Assertions.*;
import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
@WebMvcTest(controllers = HomeController.class)
@MockBean(JpaMetamodelMappingContext.class)
class HomeControllerTest {
    @Autowired
    private MockMvc mvc;

    @MockBean
    private PostService postService;

    @Test
    @WithMockUser
    void indexPage() throws Exception {
        String html = mvc.perform(
                get("/"))
                .andExpect(status().isOk())
                .andExpect(
                        content().string(
                                containsString("/blog/user")))
                .andExpect(
                        content().string(
                                containsString("/posts")))
                .andReturn()
                .getResponse()
                .getContentAsString();

        assertThat(html).contains(
                "<a class=\"nav-link\" href=\"/logout\">Logout</a>"
        );
    }
}
