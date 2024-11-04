package com.example.ahimmoyakbackend.live.dto;

import com.example.ahimmoyakbackend.live.entity.ChatMessage;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record ChatMessagePubDto(
        String username,
        String message
) {
    public ChatMessage to(long roomId) {
        return ChatMessage.builder()
                .liveId(roomId)
                .username(this.username)
                .message(this.message)
                .time(LocalDateTime.now())
                .build();
    }
}
