package com.example.ahimmoyakbackend.course.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface CurriculumService {
    public Long add(UserDetails userDetails, long courseId, String curriculumTitle);
    public boolean update(UserDetails userDetails, long curriculumId, String curriculumTitle);
    public boolean delete(UserDetails userDetails, long curriculumId);
}
