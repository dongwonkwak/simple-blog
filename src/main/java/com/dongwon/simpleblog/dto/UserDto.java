package com.dongwon.simpleblog.dto;

import com.dongwon.simpleblog.validation.EmailAlreadyExists;
import com.dongwon.simpleblog.validation.UsernameAlreadyExits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;


@UsernameAlreadyExits
@EmailAlreadyExists
public record UserDto(
        @NotNull
        @Size(min = 4, max = 10, message = "Username must be minimum 4 characters, and maximum 10 characters long")
        String username,
        @NotNull
        @Email(
                regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "Invalid Email")
        String email,
        @NotNull
        @Size(min = 4, max = 10, message = "Password must be minimum 9 characters, and maximum 32 characters long")
        String password) {
}
