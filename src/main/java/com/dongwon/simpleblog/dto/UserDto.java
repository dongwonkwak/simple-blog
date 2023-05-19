package com.dongwon.simpleblog.dto;

import com.dongwon.simpleblog.validation.EmailAlreadyExists;
import com.dongwon.simpleblog.validation.PasswordValueMatch;
import com.dongwon.simpleblog.validation.UsernameAlreadyExits;
import com.dongwon.simpleblog.validation.ValidPassword;
import jakarta.validation.constraints.*;
import lombok.Builder;

@PasswordValueMatch.List({
        @PasswordValueMatch(
                field = "password",
                fieldMatch = "confirmPassword",
                message = "{PasswordNotMach}"
        )
})
@UsernameAlreadyExits
@EmailAlreadyExists
@Builder
public record UserDto(
        @NotBlank
        @Size(min = 4, max = 10, message = "Username must be minimum 4 characters, and maximum 10 characters long")
        String username,
        @NotBlank
        @Email(flags = Pattern.Flag.CASE_INSENSITIVE, message = "Invalid Email")
        String email,
        @NotBlank
        @ValidPassword
        String password,
        @NotBlank
        @ValidPassword
        String confirmPassword) {
}
