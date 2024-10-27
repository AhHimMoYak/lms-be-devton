package com.example.ahimmoyakbackend.course.dto;


import com.example.ahimmoyakbackend.course.entity.Course;
import lombok.Builder;

import java.time.LocalDate;
import java.util.List;

@Builder
public record CourseDetailResponseDto(
        String title,
        String introduction,
        String tutor,
        LocalDate beginDate,
        LocalDate endDate,
        List<CurriculumListResponseDto> curriculumList
){
    public static CourseDetailResponseDto from(Course course, List<CurriculumListResponseDto> curriculumList) {
        return CourseDetailResponseDto.builder()
                .title(course.getTitle())
                .introduction(course.getIntroduction())
                .tutor(course.getTutor().getName())
                .beginDate(course.getBeginDate())
                .endDate(course.getEndDate())
                .curriculumList(curriculumList)
                .build();
    }
}