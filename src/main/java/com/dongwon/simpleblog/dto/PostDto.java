package com.dongwon.simpleblog.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
public record PostDto(
        Long id,
        @NotNull
        @Size(min = 3, max = 50, message = "Title must be minimum 3 characters, and maximum 50 characters long")
        String title,
        @NotNull
        @Size(min = 3, max = 5000, message = "Body must be minimum 3 characters, and maximum 5000 characters long")
        String body) {
}

