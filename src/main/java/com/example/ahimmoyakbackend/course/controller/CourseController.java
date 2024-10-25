package com.example.ahimmoyakbackend.course.controller;

import com.example.ahimmoyakbackend.course.dto.CourseDetailResponseDto;
import com.example.ahimmoyakbackend.course.dto.CourseCreateRequestDto;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDto;
import com.example.ahimmoyakbackend.course.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/course")
public class CourseController {
    private final CourseService courseService;

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseDetailResponseDto> getCourseDetail(@PathVariable Long courseId) {
        return ResponseEntity.ok(courseService.getDetail(courseId));
    }

    @PostMapping
    public ResponseEntity<String> createCourse(@AuthenticationPrincipal UserDetails userDetails, CourseCreateRequestDto requestDto) {
        return courseService.create(userDetails, requestDto) ? ResponseEntity.ok("코스 생성 완료") : ResponseEntity.badRequest().body("코스 생성 실패");
    }

    @PatchMapping("/{courseId}")
    public ResponseEntity<String> updateCourse(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long courseId, CourseCreateRequestDto requestDto) {
        return courseService.update(userDetails, courseId, requestDto) ? ResponseEntity.ok("코스 수정 성공") : ResponseEntity.badRequest().body("코스 수정 실패");
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<String> deleteCourse(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long courseId) {
        return courseService.delete(userDetails, courseId) ? ResponseEntity.ok("코스 삭제 성고") : ResponseEntity.badRequest().body("코스 삭제 실패");
    }

    @GetMapping
    public ResponseEntity<List<CourseListResponseDto>> getCoursesList(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok(courseService.getList(userDetails));
    }

}
