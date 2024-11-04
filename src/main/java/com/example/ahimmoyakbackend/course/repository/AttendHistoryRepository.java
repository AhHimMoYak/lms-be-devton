package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.live.entity.AttendHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttendHistoryRepository extends JpaRepository<AttendHistory, Long> {
}