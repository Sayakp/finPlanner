package com.finplanner.finplanner.service.auth;

import com.finplanner.finplanner.dto.auth.AuthRequestDto;
import com.finplanner.finplanner.dto.auth.UserRegistrationDto;
import com.finplanner.finplanner.dto.user.UserDto;
import com.finplanner.finplanner.exception.RegistrationValidationException;
import com.finplanner.finplanner.exception.TokenValidationException;
import com.finplanner.finplanner.mapper.UserMapper;
import com.finplanner.finplanner.model.Role;
import com.finplanner.finplanner.model.User;
import com.finplanner.finplanner.model.UserRole;
import com.finplanner.finplanner.repository.RoleRepository;
import com.finplanner.finplanner.repository.UserRepository;
import com.finplanner.finplanner.security.UserPrincipal;
import com.finplanner.finplanner.security.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.finplanner.finplanner.exception.RegistrationValidationException.RegistrationErrorCode.*;

@Service
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final AuthenticationManager authenticationManager;


    public AuthServiceImpl(UserRepository userRepository,
                           UserMapper userMapper,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           JwtUtils jwtUtils,
                           AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.jwtUtils = jwtUtils;
        this.authenticationManager = authenticationManager;
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> registerUser(UserRegistrationDto registrationDto) {
        if (userRepository.existsByUsername(registrationDto.getUsername())) {
            throw new RegistrationValidationException(USERNAME_TAKEN);
        }
        if (userRepository.existsByEmail(registrationDto.getEmail())) {
            throw new RegistrationValidationException(EMAIL_IN_USE);
        }
        User user = userMapper.toUser(registrationDto);
        user.setPasswordHash(passwordEncoder.encode(registrationDto.getPassword()));
        Role userRole = roleRepository.findByRole(UserRole.USER)
                .orElseThrow(() -> new RegistrationValidationException(ROLE_NOT_FOUND));
        user.addRole(userRole);
        User savedUser = userRepository.save(user);

        return generateTokenResponse(savedUser);
    }

    @Override
    public ResponseEntity<UserDto> authenticateUser(AuthRequestDto authRequestDto) {
        Authentication authentication = authenticationManager
                .authenticate(
                        new UsernamePasswordAuthenticationToken(
                                authRequestDto.getUsername(),
                                authRequestDto.getPassword()
                        )
                );

        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        ResponseCookie accessTokenCookie = jwtUtils.generateAccessTokenCookie(userPrincipal);
        ResponseCookie refreshTokenCookie = jwtUtils.generateRefreshTokenCookie(userPrincipal);
        UserDto responseDto = userMapper.toUserDto(userPrincipal.getUser());
        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(responseDto);
    }

    @Override
    @Transactional
    public ResponseEntity<UserDto> refreshToken(HttpServletRequest request) {
        String refreshToken = jwtUtils.getJwtFromRefreshTokenCookie(request);

        if (refreshToken == null || !jwtUtils.validateJwtToken(refreshToken)) {
            throw new TokenValidationException("Invalid or expired refresh token");
        }

        String username = jwtUtils.getUsernameFromJwtToken(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return generateTokenResponse(user);
    }

    private ResponseEntity<UserDto> generateTokenResponse(User user) {
        UserPrincipal userPrincipal = UserPrincipal.build(user);

        ResponseCookie accessTokenCookie = jwtUtils.generateAccessTokenCookie(userPrincipal);
        ResponseCookie refreshTokenCookie = jwtUtils.generateRefreshTokenCookie(userPrincipal);
        UserDto responseDto = userMapper.toUserDto(user);

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, accessTokenCookie.toString())
                .header(HttpHeaders.SET_COOKIE, refreshTokenCookie.toString())
                .body(responseDto);
    }
}
