package com.example.ahimmoyakbackend.course.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContentListResponseDTO {
    private Long id;
    private String title;
}
