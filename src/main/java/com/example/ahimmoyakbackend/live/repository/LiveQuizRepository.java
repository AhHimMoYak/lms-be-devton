package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiveQuizRepository extends JpaRepository<LiveQuiz, Long> {
    List<LiveQuiz> findByLiveStreaming(LiveStreaming liveStreaming);
}