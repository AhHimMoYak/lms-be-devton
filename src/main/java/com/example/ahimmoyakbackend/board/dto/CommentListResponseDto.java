package com.example.ahimmoyakbackend.board.dto;

import com.example.ahimmoyakbackend.board.entity.CourseComment;
import lombok.Builder;

@Builder
public record CommentListResponseDto(
    String content,
    String user,
    Long id
) {
    public static CommentListResponseDto from(CourseComment comment) {
        return CommentListResponseDto.builder()
                .content(comment.getContent())
                .user(comment.getUser().getUsername())
                .id(comment.getId())
                .build();
    }
}
