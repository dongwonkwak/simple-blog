package com.dongwon.simpleblog.controller;

import com.dongwon.simpleblog.domain.User;
import com.dongwon.simpleblog.dto.UserDto;
import com.dongwon.simpleblog.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequiredArgsConstructor
public class RegistrationController {
    private final UserService userService;

    @GetMapping("/registration")
    public ModelAndView registration(@ModelAttribute("user") UserDto userDto,
                               ModelAndView modelAndView) {
        modelAndView.addObject("user", userDto);
        modelAndView.setViewName("registration");
        return modelAndView;
    }

    @PostMapping("/registration")
    public String createUser(@ModelAttribute("user") @Valid UserDto userDto,
                             Errors errors) {
        if (errors.hasErrors()) {
            return "registration";
        }
        User user = User.builder()
                .email(userDto.email())
                .username(userDto.username())
                .password(userDto.password())
                .build();

        userService.save(user);

        return "redirect:/";
    }
}
