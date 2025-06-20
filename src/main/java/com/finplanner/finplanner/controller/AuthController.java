package com.finplanner.finplanner.controller;

import com.finplanner.finplanner.dto.auth.AuthRequestDto;
import com.finplanner.finplanner.dto.auth.UserRegistrationDto;
import com.finplanner.finplanner.dto.user.UserDto;
import com.finplanner.finplanner.security.jwt.JwtUtils;
import com.finplanner.finplanner.service.auth.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final JwtUtils jwtUtils;

    public AuthController(AuthService authService, JwtUtils jwtUtils) {
        this.authService = authService;
        this.jwtUtils = jwtUtils;
    }

    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@Valid @RequestBody UserRegistrationDto userRegistrationDto) {
        return authService.registerUser(userRegistrationDto);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserDto> signIn(@Valid @RequestBody AuthRequestDto authRequestDto) {
        return authService.authenticateUser(authRequestDto);
    }

    @PostMapping("/signout")
    public ResponseEntity<?> signOut() {
        ResponseCookie cleanAccessCookie = jwtUtils.getCleanAccessTokenCookie();
        ResponseCookie cleanRefreshToken = jwtUtils.getCleanRefreshTokenCookie();

        return ResponseEntity.noContent()
                .header(HttpHeaders.SET_COOKIE, cleanAccessCookie.toString())
                .header(HttpHeaders.SET_COOKIE, cleanRefreshToken.toString())
                .build();
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<UserDto> refreshToken(HttpServletRequest request) {
        return authService.refreshToken(request);
    }

}
