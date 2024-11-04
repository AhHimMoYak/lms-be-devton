package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import lombok.Builder;

import java.util.List;

@Builder
public record LiveQuizResponseDto(
        long id,
        String question,
        int answer,
        String solution,
        List<LiveQuizOptionDto> options
) {
    public static LiveQuizResponseDto from(LiveQuiz liveQuiz, List<LiveQuizOptionDto> options) {
        return LiveQuizResponseDto.builder()
                .id(liveQuiz.getId())
                .question(liveQuiz.getQuestion())
                .answer(liveQuiz.getAnswer())
                .solution(liveQuiz.getSolution())
                .options(options)
                .build();
    }
}
