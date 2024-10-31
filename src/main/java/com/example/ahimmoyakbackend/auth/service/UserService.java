package com.example.ahimmoyakbackend.auth.service;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.auth.dto.*;
import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.exception.InvalidPasswordException;
import com.example.ahimmoyakbackend.auth.jwt.JwtTokenProvider;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;


    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Transactional
    public UserJoinResponseDTO create(UserJoinRequestDTO requestDto) {

        User user = User.builder()
                .username(requestDto.getUsername())
                .name(requestDto.getName())
                .password(passwordEncoder.encode(requestDto.getPassword()))
                .birth(requestDto.getBirth())
                .email(requestDto.getEmail())
                .gender(requestDto.getGender())
                .tutorState(requestDto.isTutorState())
                .build();

        userRepository.save(user);

        return UserJoinResponseDTO.builder()
                .msg("회원가입 완료")
                .build();
    }

    public UserLoginResponseDTO login(UserLoginRequestDTO requestDTO, HttpServletResponse response) {
        User findUser = userRepository.findByUsername(requestDTO.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new IllegalArgumentException("잘못된 요청입니다.");
        }

        JwsDTO jwsDto = jwtTokenProvider.createAllToken(findUser.getUsername(), findUser.getEmail(), findUser.isTutorState());

        response.addHeader(JwtTokenProvider.ACCESS_TOKEN, jwsDto.getAccessToken());
        response.addHeader(JwtTokenProvider.REFRESH_TOKEN, jwsDto.getRefreshToken());

        return UserLoginResponseDTO.builder()
                .msg("로그인 완료")
                .build();
    }

    public User getAuth(UserDetails userDetails) {
        return userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                ()-> new IllegalArgumentException("인증되지 않은 사용자입니다.")
        );

    }

    public UserReissueResponseDTO reissue() {

        return UserReissueResponseDTO.builder()
                .msg("refresh token reissue complete")
                .build();
    }

    public ExistNameResponseDTO checkExistName(ExistNameRequestDTO requestDTO) {
        try {
            if(!userRepository.existsByUsername(requestDTO.getUsername())) {
                throw new IllegalArgumentException("잘못된 요청입니다.");
            }
        } catch (Exception e) {
            return ExistNameResponseDTO.builder()
                    .message("true")
                    .build();
        }

        return ExistNameResponseDTO.builder()
                .message("false")
                .build();
    }

    public UserVerificationResponseDTO checkVerification(UserVerificationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        if (!passwordEncoder.matches(requestDTO.getPassword(), findUser.getPassword())) {
            throw new InvalidPasswordException("false");
        }
        return UserVerificationResponseDTO
                .builder()
                .message("true")
                .build();
    }

    public UserInformationResponseDTO updatePersonalInformation(UserInformationRequestDTO requestDTO, UserDetailsImpl userDetails) {
        User findUser = userRepository.findByUsername(userDetails.getUsername()).orElseThrow(
                () -> new IllegalArgumentException("잘못된 요청입니다.")
        );

        findUser.patch(requestDTO, passwordEncoder.encode(requestDTO.getPassword()));
        userRepository.save(findUser);

        return UserInformationResponseDTO.builder()
                .message("complete")
                .build();
    }

}