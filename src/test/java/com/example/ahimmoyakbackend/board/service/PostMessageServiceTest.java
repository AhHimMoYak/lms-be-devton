package com.example.ahimmoyakbackend.board.service;

import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.board.dto.*;
import com.example.ahimmoyakbackend.board.repository.PostMessageRepository;
import com.example.ahimmoyakbackend.board.repository.TargetUserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class PostMessageServiceTest {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMessageRepository postMessageRepository;

    @Autowired
    private TargetUserRepository targetUserRepository;

    @Autowired
    private PostMessageService postMessageService;

    @BeforeEach
    void setUp() {
    }


}
