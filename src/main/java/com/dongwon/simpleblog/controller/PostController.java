package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.model.Post;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private final UserService userService;

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        Optional<Post> optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();
            model.addAttribute("post", post);
            return "/post";
        } else {
            return "/404";
        }
    }

    @GetMapping("/postNew")
    public String createNewPost(Model model) {
        String email = "homer@springfield.com";
        var optionalUser = userService.findByEmail(email);

        if (optionalUser.isPresent()) {
            Post post = new Post();
            post.setUser(optionalUser.get());
            model.addAttribute("post", post);
            return "/postForm";
        } else {
            return "/404";
        }
    }

    @PostMapping("/postNew")
    public String createNewPost(@Valid Post post,
                                BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "/postForm";
        }
        postService.save(post);
        return "redirect:/home";
    }

    @GetMapping("/editPost/{id}")
    public String editPost(@PathVariable Long id,
                           Model model) {
        var optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            Post post = optionalPost.get();

            model.addAttribute("post", post);
            return "/postForm";
        }

        return "/404";
    }

}
