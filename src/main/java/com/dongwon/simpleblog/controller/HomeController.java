package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@Controller
@RequiredArgsConstructor
public class HomeController {
    private final PostService postService;

    @GetMapping("/")
    public String home(@RequestParam(defaultValue = "0") int page,
                       Model model) {
        var posts = postService.findAll(page);

        model.addAttribute("posts", posts);
        model.addAttribute("page", page);
        return "home";
    }
}
