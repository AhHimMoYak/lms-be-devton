package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.common.CourseState;
import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record CourseListResponseDto(
   long id,
   String title,
   String introduction,
   String tutor,
   LocalDate beginDate,
   LocalDate endDate,
   CourseState state,
   CourseCategory category
) {
    public static CourseListResponseDto from(Course course) {
        return CourseListResponseDto.builder()
                .id(course.getId())
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .tutor(course.getTutor().getName())
                .beginDate(course.getBeginDate())
                .endDate(course.getEndDate())
                .state(course.getState())
                .category(course.getCategory())
                .build();
    }
}
