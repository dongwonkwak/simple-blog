package com.dongwon.simpleblog.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    @GetMapping("/registration")
    public String registration(Model model) {
        return "/registration";
    }
}
