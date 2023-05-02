package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.model.User;
import com.dongwon.simpleblog.service.PostService;
import com.dongwon.simpleblog.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class BlobController {
    private final UserService userService;
    private final PostService postService;

    @GetMapping("/blog/{username}")
    public String getBlog(@PathVariable String username,
                          @RequestParam(defaultValue = "0") int page,
                          Model model) {
        var optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            var posts = postService.findByUserWithPage(user, page);

            model.addAttribute("user", user);
            model.addAttribute("posts", posts);
            return "/posts";
        } else {
            return "/404";
        }
    }
}
