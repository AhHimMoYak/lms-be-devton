package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.course.repository.CurriculumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CurriculumServiceImpl implements CurriculumService {

    private final UserService userService;
    private final CourseRepository courseRepository;
    private final CurriculumRepository curriculumRepository;

    @Override
    @Transactional
    public Long add(UserDetails userDetails, long courseId, String curriculumTitle) {
        Course course = courseRepository.findById(courseId).orElse(null);
//        if (course == null || !course.getTutor().equals(userService.getAuth(userDetails))) {
//            return ;
//        }
        long count = curriculumRepository.countByCourse(course);
        return curriculumRepository.save(Curriculum.builder()
                .title(curriculumTitle)
                .idx((int)(count+1))
                .course(course)
                .build()).getId();

    }

    @Override
    @Transactional
    public boolean update(UserDetails userDetails, long curriculumId, String curriculumTitle) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        if (curriculum == null || !curriculum.getCourse().getTutor().equals(userService.getAuth(userDetails))) {
            return false;
        }
        curriculumRepository.save(curriculum.patch(curriculumTitle));
        return true;
    }

    @Override
    @Transactional
    public boolean delete(UserDetails userDetails, long curriculumId) {
        Curriculum curriculum = curriculumRepository.findById(curriculumId).orElse(null);
        if (curriculum == null || !curriculum.getCourse().getTutor().equals(userService.getAuth(userDetails))|| !curriculum.getContentsList().isEmpty()) {
            return false;
        }
        curriculumRepository.delete(curriculum);
        return true;
    }
}
