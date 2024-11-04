package com.example.ahimmoyakbackend.live.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.util.Map;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "quiz")
public class QuizStatus {
    @Id
    private long id;
    @Indexed
    private long liveId;
    private Map<Integer, Integer> options;
}
