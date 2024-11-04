package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import com.example.ahimmoyakbackend.live.entity.LiveQuizOption;
import lombok.Builder;

@Builder
public record LiveQuizOptionDto(
        String text,
        int idx
) {
    public LiveQuizOption toEntity(LiveQuiz liveQuiz) {
        return LiveQuizOption.builder()
                .liveQuiz(liveQuiz)
                .text(this.text)
                .idx(this.idx)
                .build();
    }

    public static LiveQuizOptionDto from(LiveQuizOption liveQuizOption) {
        return LiveQuizOptionDto.builder()
                .text(liveQuizOption.getText())
                .idx(liveQuizOption.getIdx())
                .build();
    }
}
