package com.example.ahimmoyakbackend.board.repository;

import com.example.ahimmoyakbackend.board.entity.CourseComment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CourseCommentRepository extends JpaRepository<CourseComment, Long> {
    List<CourseComment> findAllByCourseBoardId(Long courseBoardId);
}