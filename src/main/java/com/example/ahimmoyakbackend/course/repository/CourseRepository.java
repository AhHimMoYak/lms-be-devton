package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByEnrollments_User(User user);

    List<Course> findAllByTutor(User tutor);

    List<Course> findAllByCategory(CourseCategory category);

    Page<Course> findAllByCategory(CourseCategory category, Pageable pageable);

}