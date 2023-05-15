package com.dongwon.simpleblog.controller;

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

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        var optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            var post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
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
            return "redirect:/blog/" + authentication.getName();
        }
        catch (SimpleBlogException ex) {
            return "404";
        }
    }

    @PutMapping("/{id}")
    public String editPost(@PathVariable Long id,
                           Model model) {
        var optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {
            model.addAttribute("post", optionalPost.get());
            return "postForm";
        }

        return "404";
    }

    @DeleteMapping("/{id}")
    public String deletePost(@PathVariable Long id,
                             Authentication authentication) {
        var postDto = postService.findById(id);
        if (postDto.isPresent()) {
            postService.delete(id);
            return "redirect:/blog/" + authentication.getName();
        }

        return "404";
    }

    @ExceptionHandler(SimpleBlogException.class)
    public ModelAndView handleSimpleBlogException(SimpleBlogException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex);
        return model;
    }
}
