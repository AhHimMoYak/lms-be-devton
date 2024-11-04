package com.example.ahimmoyakbackend.live.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "answer")
public class QuizAnswer {
    @Id
    private long id;
    @Indexed
    private long quizId;
    @Indexed
    private long liveId;
    private int answer;
}
