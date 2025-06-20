package com.finplanner.finplanner.security.jwt;

import com.finplanner.finplanner.security.UserPrincipal;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseCookie;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;

@Component
public class JwtUtils {
    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    private final String API_PATH = "/api";
    private final String REFRESH_COOKIE_PATH = "/api/auth/refreshtoken";

    private final String jwtSecret;
    private final int accessTokenExpirationMs;
    private final int refreshTokenExpirationMs;
    private final String accessTokenCookieName;
    private final String refreshTokenCookieName;
    private final int accessTokenExpirationSeconds;
    private final int refreshTokenExpirationSeconds;

    public JwtUtils(
            @Value("${app.jwt.secret}") String jwtSecret,
            @Value("${app.jwt.accessTokenExpirationMs}") int accessTokenExpirationMs,
            @Value("${app.jwt.refreshTokenExpirationMs}") int refreshTokenExpirationMs,
            @Value("${app.jwt.accessTokenCookieName}") String accessTokenCookieName,
            @Value("${app.jwt.refreshTokenCookieName}") String refreshTokenCookieName
    ) {
        this.jwtSecret = jwtSecret;
        this.accessTokenExpirationMs = accessTokenExpirationMs;
        this.refreshTokenExpirationMs = refreshTokenExpirationMs;
        this.accessTokenCookieName = accessTokenCookieName;
        this.refreshTokenCookieName = refreshTokenCookieName;
        this.accessTokenExpirationSeconds = accessTokenExpirationMs / 1000;
        this.refreshTokenExpirationSeconds = refreshTokenExpirationMs / 1000;
    }

    public String getJwtFromAccessTokenCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, accessTokenCookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    public ResponseCookie generateAccessTokenCookie(UserPrincipal userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), accessTokenExpirationMs);
        return ResponseCookie.from(accessTokenCookieName, jwt)
                .path(API_PATH)
                .maxAge(accessTokenExpirationSeconds)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .build();
    }

    public ResponseCookie getCleanAccessTokenCookie() {
        return ResponseCookie.from(accessTokenCookieName, "")
                .path(API_PATH)
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Lax")
                .build();
    }

    public String getJwtFromRefreshTokenCookie(HttpServletRequest request) {
        Cookie cookie = WebUtils.getCookie(request, refreshTokenCookieName);
        return cookie != null ? cookie.getValue() : null;
    }

    public ResponseCookie generateRefreshTokenCookie(UserPrincipal userPrincipal) {
        String jwt = generateTokenFromUsername(userPrincipal.getUsername(), refreshTokenExpirationMs);
        return ResponseCookie.from(refreshTokenCookieName, jwt)
                .path(REFRESH_COOKIE_PATH)
                .maxAge(refreshTokenExpirationSeconds)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public ResponseCookie getCleanRefreshTokenCookie() {
        return ResponseCookie.from(refreshTokenCookieName, "")
                .path(REFRESH_COOKIE_PATH)
                .maxAge(0)
                .httpOnly(true)
                .secure(true)
                .sameSite("Strict")
                .build();
    }

    public String getUsernameFromJwtToken(String token) {
        return Jwts.parser()
                .verifyWith(key())
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();
    }

    private SecretKey key() {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
    }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().verifyWith(key()).build().parseSignedClaims(authToken);
            return true;
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            logger.error("Expired JWT token: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            logger.error("Unsupported JWT token: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty: {}", e.getMessage());
        }

        return false;
    }

    public String generateTokenFromUsername(String username, int expirationMs) {
        Instant now = Instant.now();
        Instant expiration = now.plusMillis(expirationMs);

        return Jwts.builder()
                .subject(username)
                .issuedAt(Date.from(now))
                .expiration(Date.from(expiration))
                .signWith(key())
                .compact();
    }
}


