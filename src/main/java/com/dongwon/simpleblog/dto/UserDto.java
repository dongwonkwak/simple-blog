package com.dongwon.simpleblog.dto;

import com.dongwon.simpleblog.validation.EmailAlreadyExists;
import com.dongwon.simpleblog.validation.UsernameAlreadyExits;
import jakarta.validation.constraints.*;


@UsernameAlreadyExits
@EmailAlreadyExists
public record UserDto(
        @NotBlank
        @Size(min = 4, max = 10, message = "Username must be minimum 4 characters, and maximum 10 characters long")
        String username,
        @NotNull
        @Email(
                regexp = "[a-z0-9._%+-]+@[a-z0-9.-]+\\.[a-z]{2,3}",
                flags = Pattern.Flag.CASE_INSENSITIVE,
                message = "Invalid Email")
        String email,
        @NotBlank
        @Size(min = 4, max = 10, message = "Password must be minimum 9 characters, and maximum 32 characters long")
        String password) {
}
