package com.dongwon.simpleblog.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler
    public String servletRequestBindingException(ServletRequestBindingException e) {
        log.error("SimpleBlogException occurred: " + e.getMessage());
        return "error";
    }
}
