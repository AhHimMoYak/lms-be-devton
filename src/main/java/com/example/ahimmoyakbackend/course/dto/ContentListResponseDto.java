package com.example.ahimmoyakbackend.course.dto;

import com.example.ahimmoyakbackend.course.common.ContentType;
import com.example.ahimmoyakbackend.course.entity.Contents;
import lombok.Builder;

@Builder
public record ContentListResponseDto(
    long id,
    ContentType type,
    String title
) {
    public static ContentListResponseDto from(Contents contents) {
        return ContentListResponseDto.builder()
                .id(contents.getId())
                .type(contents.getType())
                .title(contents.getTitle())
                .build();
    }
}
