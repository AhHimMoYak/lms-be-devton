package com.example.ahimmoyakbackend.live.dto;

import lombok.Builder;

@Builder
public record QuizAnswerMsgDto(
        String username,
        long quizId,
        int answer
) {
}
