package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public String registration(Model model) {
        model.addAttribute("user", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String createUser(@Valid User user,
                             BindingResult bindingResult,
                             Model model) {
        // 이미 가입된 이메일 정보가 존재한다.
        if (userService.findByEmail(user.getEmail()).isPresent()) {
            bindingResult.rejectValue("email", "이미 등록된 이메일입니다.");
        }

        if (userService.findByUsername(user.getUsername()).isPresent()) {
            bindingResult
                    .rejectValue("username", "이미 등록된 사용자입니다.");
        }
        if (!bindingResult.hasErrors()) {
            userService.save(user);
        }

        return "redirect:/";
    }
}
