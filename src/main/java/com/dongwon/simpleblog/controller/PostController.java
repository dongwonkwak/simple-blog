package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.dto.PostDto;
import com.dongwon.simpleblog.exception.SimpleBlogException;
import com.dongwon.simpleblog.mapper.PostMapper;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
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
    private final UserService userService;
    private final PostMapper postMapper;

    @GetMapping("/{id}")
    public String getPost(@PathVariable Long id, Model model) {
        var optionalPost = postService.findById(id);

        if (optionalPost.isPresent()) {
            PostDto post = optionalPost.get();
            model.addAttribute("post", post);
            return "post";
        } else {
            return "404";
        }
    }

    @GetMapping("/new")
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
        var optionalUser = userService.findByUsername(authentication.getName());
        if (optionalUser.isPresent()) {
            postDto.setUser(optionalUser.get());
            postService.save(postDto);
            return "redirect:/blog/" + authentication.getName();
        }

        return "404";
    }

    @PostMapping("/{id}/edit")
    public String editPost(@PathVariable Long id,
                           Model model) {
        var optionalPost = postService.findById(id);
        if (optionalPost.isPresent()) {

        }

        return "editPostForm";
    }


    /*@GetMapping("/posts/{id}")
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
    }*/
    @ExceptionHandler(SimpleBlogException.class)
    public ModelAndView handleSimpleBlogException(SimpleBlogException ex) {
        ModelAndView model = new ModelAndView("error");
        model.addObject("exception", ex);
        return model;
    }
}
