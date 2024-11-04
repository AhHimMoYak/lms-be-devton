package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.live.dto.ChatMessagePubDto;
import com.example.ahimmoyakbackend.live.dto.ChatMessageSubDto;
import com.example.ahimmoyakbackend.live.entity.ChatMessage;
import com.example.ahimmoyakbackend.live.repository.ChatMessageRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStatusRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatMessageRepository chatMessageRepository;
    private final LiveStatusRepository liveStatusRepository;

    public ChatMessageSubDto message(long liveId, ChatMessagePubDto dto) {
        if (!liveStatusRepository.existsById(liveId)) {
            return null;
        }
        ChatMessage chatMessage = chatMessageRepository.save(dto.to(liveId));
        return ChatMessageSubDto.from(chatMessage);
    }

    public List<ChatMessageSubDto> getAll(long liveId) {
        return chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId).stream()
                .map(ChatMessageSubDto::from)
                .toList();
    }

    public void deleteAll(long liveId) {
        List<ChatMessage> messages = chatMessageRepository.findAllByLiveIdOrderByTimeAsc(liveId);
        chatMessageRepository.deleteAll(messages);
    }
}
