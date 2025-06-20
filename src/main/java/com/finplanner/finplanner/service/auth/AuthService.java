package com.finplanner.finplanner.service.auth;

import com.finplanner.finplanner.dto.auth.AuthRequestDto;
import com.finplanner.finplanner.dto.auth.UserRegistrationDto;
import com.finplanner.finplanner.dto.user.UserDto;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<UserDto> registerUser(UserRegistrationDto registrationDto);
    ResponseEntity<UserDto> authenticateUser(AuthRequestDto authRequestDto);
    ResponseEntity<UserDto> refreshToken(HttpServletRequest request);
}
