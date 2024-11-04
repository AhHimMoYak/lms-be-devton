package com.example.ahimmoyakbackend.live.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "msg")
public class ChatMessage {

    @Id
    private Long id;

    @Indexed
    private Long liveId;

    private String username;

    private String message;

    private LocalDateTime time;
}
