package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.dto.CourseDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseCreateRequestDto;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface CourseService {
    public CourseDetailResponseDto getDetail(long id);
    public boolean create(UserDetails userDetails, CourseCreateRequestDto requestDto);
    public boolean update(UserDetails userDetails, long id, CourseCreateRequestDto requestDto);
    public boolean delete(UserDetails userDetails, long id);
    public List<CourseListResponseDto> getList(UserDetails userDetails);
    public List<CourseListResponseDto> getAllList();
}