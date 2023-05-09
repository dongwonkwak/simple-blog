package com.dongwon.simpleblog.dto;


import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
    private Long id;

    @NotNull
    @Size(min = 4, max = 10, message = "Username must be minimum 4 characters, and maximum 10 characters long")
    private String username;

    @NotNull
    @Email
    private String email;

    @NotNull
    @Size(min = 4, max = 10, message = "Password must be minimum 9 characters, and maximum 32 characters long")
    private String password;
}
