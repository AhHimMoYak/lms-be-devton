package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.course.common.CourseCategory;
import com.example.ahimmoyakbackend.course.dto.CourseListResponseDTO;
import com.example.ahimmoyakbackend.course.dto.CourseRegistrationRequestDTO;
import com.example.ahimmoyakbackend.course.dto.CourseResponseDTO;
import com.example.ahimmoyakbackend.course.dto.TutorGetCourseListResponseDTO;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final TutorRepository tutorRepository;
    private final AffiliationRepository affiliationRepository;
    private final CourseProvideRepository courseProvideRepository;

    // 마이페이지 코스리스트 조회
    @Transactional
    public Page<CourseListResponseDTO> findUserCourseList(User user, Long institutionId, int page, @Positive int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        if (user.getId() == null || institutionId == null) {
            return null;
        }
        Page<Course> coursePage = courseRepository.findAll(pageable);
        List<CourseListResponseDTO> list = coursePage.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage().getPath())
                        .build())
                .toList();
        return new PageImpl<>(list, coursePage.getPageable(), coursePage.getTotalElements());
    }

    // 수강신청할 코스 탐색
    @Transactional
    public Page<CourseListResponseDTO> getCourseByCategory(int categoryNum, int currentPage, int size) {
        Pageable pageable = PageRequest.of(currentPage - 1, size);
        CourseCategory category = Arrays.stream(CourseCategory.values())
                .filter(course -> course.getCategoryNumber() == categoryNum).findFirst().get();
        if (CourseCategory.ALL.getCategoryNumber() == categoryNum) {
            return courseRepository.findAll(pageable)
                    .map(course -> CourseListResponseDTO.builder()
                            .id(course.getId())
                            .title(course.getTitle())
                            .image(course.getImage().getPath())
                            .tutor(course.getTutor())
                            .build());
        }
        Page<Course> page = courseRepository.findAllByCategory(category, pageable);
        List<CourseListResponseDTO> list = page.stream()
                .map(course -> CourseListResponseDTO.builder()
                        .id(course.getId())
                        .title(course.getTitle())
                        .image(course.getImage().getPath())
                        .tutor(course.getTutor())
                        .build())
                .toList();
        return new PageImpl<>(list, page.getPageable(), page.getTotalElements());
    }

    // 강사 대시보드리스트 조회
    @Transactional
    public List<TutorGetCourseListResponseDTO> getCurriculumList(String username) {
        Tutor tutor = tutorRepository.findByUserName(username)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 유저입니다."));
        List<Course> courseList = courseRepository.findByTutor(tutor);
        List<TutorGetCourseListResponseDTO> tutorGetCourseList = new ArrayList<>();
        for (Course course : courseList) {
            tutorGetCourseList.add(TutorGetCourseListResponseDTO.builder()
                    .id(course.getId())
                    .title(course.getTitle())
                    .image(course.getImage().getPath())
                    .category(course.getCategory())
                    .build());
        }
        return tutorGetCourseList;
    }

    // 수강신청 요청
    @Transactional
    public CourseResponseDTO createCourseFormRegistration(
            User user, Long courseId, CourseRegistrationRequestDTO requestDTO
    ) {
        Affiliation affiliation = affiliationRepository.findByUser(user);
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지않는 코스입니다."));
        CourseProvide courseProvide = CourseProvide.builder()
                .course(course)
                .beginDate(requestDTO.getBeginDate())
                .endDate(requestDTO.getEndDate())
                .attendeeCount(requestDTO.getAttendeeCount())
                .supervisor(affiliation)
                .build();
        courseProvideRepository.save(courseProvide);
        return CourseResponseDTO.builder()
                .msg("수강신청 양식 제출완료.")
                .build();
    }
}