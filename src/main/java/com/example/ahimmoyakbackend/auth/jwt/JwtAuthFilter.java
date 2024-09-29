package com.example.ahimmoyakbackend.auth.jwt;

import com.example.ahimmoyakbackend.auth.dto.validation.SecurityExceptionDto;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.ahimmoyakbackend.auth.jwt.JwtTokenProvider.ACCESS_TOKEN;
import static com.example.ahimmoyakbackend.auth.jwt.JwtTokenProvider.REFRESH_TOKEN;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String accessToken = jwtTokenProvider.getTokenFromHeader(request, ACCESS_TOKEN);
        String refreshToken = jwtTokenProvider.getTokenFromHeader(request, REFRESH_TOKEN);

        if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                setAuthentication(jwtTokenProvider.getUserInfoFromToken(accessToken));
                filterChain.doFilter(request, response);
            } else {
                jwtExceptionHandler(response, "Invalid Access Token.", HttpStatus.UNAUTHORIZED.value());
            }
            return;
        }

        if (refreshToken != null && jwtTokenProvider.refreshTokenValid(refreshToken)) {
            String username = jwtTokenProvider.getUserInfoFromToken(refreshToken);
            User user = userRepository.findUserByUsername(username)
                    .orElseThrow(() -> new IllegalArgumentException("User not found"));
            String newAccessToken = jwtTokenProvider.createRefreshToken(username);
            jwtTokenProvider.setHeaderAccessToken(response, newAccessToken);
            setAuthentication(username);
            filterChain.doFilter(request, response);
            return;
        }

        if (refreshToken != null) {
            jwtExceptionHandler(response, "Invalid or Expired Refresh Token.", HttpStatus.UNAUTHORIZED.value());
            return;
        }

        filterChain.doFilter(request, response);

    }

    public void setAuthentication(String username) {
        Authentication authentication = jwtTokenProvider.getAuthentication(username);
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public void jwtExceptionHandler(HttpServletResponse response, String msg, int statusCode) {
        response.setStatus(statusCode);
        response.setContentType("application/json");
        try {
            String json = new ObjectMapper().writeValueAsString(SecurityExceptionDto.builder()
                    .statusCode(statusCode)
                    .msg(msg)
                    .build());
            response.getWriter().write(json);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

}