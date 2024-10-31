package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.entity.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByEnrollments_User(User user);

    List<Course> findAllByTutor(User tutor);

    List<Course> findAllByCategory(CourseCategory category);

    Page<Course> findAllByCategory(CourseCategory category, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.category = :category AND c.state != 'REMOVED' " +
            "ORDER BY CASE WHEN c.state = 'NOT_STARTED' THEN 1 " +
            "WHEN c.state = 'ONGOING' THEN 2 " +
            "WHEN c.state = 'FINISHED' THEN 3 ELSE 4 END ASC")
    List<Course> findAllByCategoryOrderByState(CourseCategory category);

    @Query("SELECT c FROM Course c WHERE c.category = :category AND c.state != 'REMOVED' " +
            "ORDER BY CASE WHEN c.state = 'NOT_STARTED' THEN 1 " +
            "WHEN c.state = 'ONGOING' THEN 2 " +
            "WHEN c.state = 'FINISHED' THEN 3 ELSE 4 END ASC")
    Page<Course> findAllByCategoryOrderByState(CourseCategory category, Pageable pageable);

    @Query("SELECT c FROM Course c WHERE c.state != 'REMOVED' " +
            "ORDER BY CASE WHEN c.state = 'NOT_STARTED' THEN 1 " +
            "WHEN c.state = 'ONGOING' THEN 2 " +
            "WHEN c.state = 'FINISHED' THEN 3 ELSE 4 END ASC")
    List<Course> findAllOrderByState();

    @Query("SELECT c FROM Course c WHERE c.state != 'REMOVED' " +
            "ORDER BY CASE WHEN c.state = 'NOT_STARTED' THEN 1 " +
            "WHEN c.state = 'ONGOING' THEN 2 " +
            "WHEN c.state = 'FINISHED' THEN 3 ELSE 4 END ASC")
    Page<Course> findAllOrderByState(Pageable pageable);

}