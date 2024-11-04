package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.live.dto.LiveQuizCreateRequestDto;
import com.example.ahimmoyakbackend.live.dto.LiveQuizResponseDto;
import com.example.ahimmoyakbackend.live.dto.QuizAnswerMsgDto;
import com.example.ahimmoyakbackend.live.dto.QuizStatusSubDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface LiveQuizService {
    public boolean create(UserDetails userDetails, long liveId, LiveQuizCreateRequestDto requestDto);
    public List<LiveQuizResponseDto> getList(long liveId);
    public LiveQuizResponseDto send(long liveId, long quizId);
    public QuizStatusSubDto answer(long liveId, QuizAnswerMsgDto pubDto);
}
