package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.ChatMessagePubDto;
import com.example.ahimmoyakbackend.live.dto.ChatMessageSubDto;
import com.example.ahimmoyakbackend.live.service.ChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;

    @MessageMapping("/chat/{liveId}")
    public ResponseEntity<ChatMessageSubDto> message(@DestinationVariable long liveId, ChatMessagePubDto dto) {
        ChatMessageSubDto subDto = chatService.message(liveId, dto);
        if (subDto == null) {
            return ResponseEntity.notFound().build();
        }
        messagingTemplate.convertAndSend("/sub/chat/" + liveId, subDto);
        return ResponseEntity.ok(subDto);
    }

    @GetMapping("/api/v1/live/{liveId}/chat")
    public ResponseEntity<List<ChatMessageSubDto>> getAllMessage(@PathVariable long liveId) {
        return ResponseEntity.ok(chatService.getAll(liveId));
    }
}
