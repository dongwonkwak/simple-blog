package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.domain.Post;
import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.exception.SimpleBlogException;
import com.dongwon.simpleblog.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;
    private static final String REDIRECT_BLOG_URL = "redirect:/blog/";

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        return postService.findById(id)
                .map(post -> {
                    model.addAttribute("post", post);
                    return "post";
                })
                .orElse("404");
    }

    @GetMapping
    public ModelAndView createPost(@ModelAttribute("post") PostDto postDto,
                                   ModelAndView modelAndView) {
        modelAndView.addObject("post", postDto);
        modelAndView.setViewName("postForm");
        return modelAndView;
    }

    @PostMapping
    public String createPost(@ModelAttribute("post") @Valid PostDto postDto,
                             Errors errors,
                             Authentication authentication) {
        if (errors.hasErrors()) {
            return "postForm";
        }

        try {
            postService.create(postDto, authentication.getName());
            return REDIRECT_BLOG_URL + authentication.getName();
        }
        catch (SimpleBlogException ex) {
            return "404";
        }
    }

    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id,
                           @Valid PostDto postDto,
                           Errors errors) {
        if (errors.hasErrors()) {
            return "post";
        }

        return postService.findById(id)
            .map(post -> {
                post.setTitle(postDto.title());
                post.setBody(postDto.body());
                postService.update(post);
                return REDIRECT_BLOG_URL + post.getUser().getUsername();
            }).orElse("404");
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id) {
        return postService.findById(id)
            .map(post -> {
                postService.delete(id);
                return REDIRECT_BLOG_URL + post.getUser().getUsername();
            })
            .orElse("404");
    }

    @ExceptionHandler(SimpleBlogException.class)
    public ModelAndView handleSimpleBlogException(SimpleBlogException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex);
        return model;
    }
}
