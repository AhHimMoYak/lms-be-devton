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
import java.util.Objects;

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

        if(Objects.equals(request.getRequestURI(), "/api/v1/reissue")){
            if(refreshToken == null) {
                jwtExceptionHandler(response, "refresh token is not found.", HttpStatus.UNAUTHORIZED.value());
                return;
            }
            if (jwtTokenProvider.validateToken(refreshToken)) {
                String username = jwtTokenProvider.getUserInfoFromToken(accessToken);
                String refresh_username = jwtTokenProvider.getUserInfoFromToken(refreshToken);
                if(username == null || refresh_username == null || !Objects.equals(username, refresh_username)){
                    jwtExceptionHandler(response, "Invalid Tokens or Not same user.", HttpStatus.UNAUTHORIZED.value());
                    return;
                }
                User user = userRepository.findByUsername(refresh_username)
                        .orElseThrow(() -> new IllegalArgumentException("User not found"));

                String newAccessToken = jwtTokenProvider.createAccessToken(refresh_username, user.getEmail(), user.isTutorState());
                response.setHeader(ACCESS_TOKEN, newAccessToken);
            }
            else {
                jwtExceptionHandler(response, "Invalid Tokens.", HttpStatus.UNAUTHORIZED.value());
                return;
            }
        } else if (accessToken != null) {
            if (jwtTokenProvider.validateToken(accessToken)) {
                setAuthentication(jwtTokenProvider.getUserInfoFromToken(accessToken));
            } else {
                jwtExceptionHandler(response, "Invalid Access Token.", HttpStatus.UNAUTHORIZED.value());
                return;
            }
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