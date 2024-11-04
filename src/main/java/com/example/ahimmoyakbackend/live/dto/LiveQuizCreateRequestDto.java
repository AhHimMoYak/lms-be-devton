package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import com.example.ahimmoyakbackend.live.entity.LiveQuizOption;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import lombok.Builder;

import java.util.List;

@Builder
public record LiveQuizCreateRequestDto (
        String question,
        int answer,
        String solution,
        List<LiveQuizOptionDto> options
) {
    public LiveQuiz toEntity(LiveStreaming live) {
        return LiveQuiz.builder()
                .liveStreaming(live)
                .question(this.question)
                .answer(this.answer)
                .solution(this.solution)
                .build();
    }
}
