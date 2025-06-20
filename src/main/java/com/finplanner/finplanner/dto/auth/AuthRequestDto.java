package com.finplanner.finplanner.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AuthRequestDto {
    @NotBlank
    private String username;
    @NotBlank
    private String password;
}
