package com.example.ahimmoyakbackend.course.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.CourseProvide;
import com.example.ahimmoyakbackend.company.repository.CourseProvideRepository;
import com.example.ahimmoyakbackend.contents.dto.ContentsInquiryResponseDTO;
import com.example.ahimmoyakbackend.course.common.ContentsHistoryState;
import com.example.ahimmoyakbackend.course.dto.*;
import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsHistory;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.entity.Curriculum;
import com.example.ahimmoyakbackend.course.repository.ContentsHistoryRepository;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumInquiryResponseDTO;
import com.example.ahimmoyakbackend.institution.entity.Institution;
import com.example.ahimmoyakbackend.institution.entity.Tutor;
import com.example.ahimmoyakbackend.institution.repository.TutorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CourseService {
    private final CourseRepository courseRepository;
    private final ContentsHistoryRepository contentsHistoryRepository;
    private final CourseProvideRepository courseProvideRepository;
    private final TutorRepository tutorRepository;


    @Transactional
    public CourseDetailsInquiryResponseDTO inquiry(User user, Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(() -> new IllegalArgumentException("코스가 없습니다."));
        CourseProvide courseProvide = courseProvideRepository.findByCourseAndEnrollments_User(course, user).orElseThrow(() -> new IllegalArgumentException("컨텐츠가 없습니다."));
        CourseDetailsInquiryResponseDTO courseDto = CourseDetailsInquiryResponseDTO.from(course, courseProvide);

        List<CurriculumInquiryResponseDTO> curriculumDTOList = new ArrayList<>();
        int courseTotalContents = 0;
        int courseCompletedContents = 0;

        for (Curriculum curriculum : course.getCurriculumList()) {

            CurriculumInquiryResponseDTO curriculumDto = CurriculumInquiryResponseDTO.from(curriculum);
            List<ContentsInquiryResponseDTO> contentsDTOList = new ArrayList<>();

            int completedContents = 0;

            for (Contents contents : curriculum.getContentsList()) {

                ContentsHistory history = contentsHistoryRepository.findByContentsAndEnrollment_User(contents, user).orElseThrow(() -> new IllegalArgumentException("컨텐츠의 히스토리가 없습니다."));
                ContentsInquiryResponseDTO contentsDTO = ContentsInquiryResponseDTO.from(contents, history.getState());
                courseTotalContents++;

                if (history.getState().equals(ContentsHistoryState.COMPLETED)) {
                    courseCompletedContents++;
                    completedContents++;
                }
                contentsDTOList.add(contentsDTO);
            }
            curriculumDto.setCompletedContents(completedContents)
                    .setContents(contentsDTOList);
            curriculumDTOList.add(curriculumDto);
        }
        courseDto.setCompletedContents(courseCompletedContents)
                .setTotalContents(courseTotalContents)
                .setCurriculums(curriculumDTOList);
        return courseDto;

    }
    @Transactional
    public CourseCreateResponseDTO create(User user, CourseCreateRequestDTO dto) {
        Tutor tutor = tutorRepository.findByUser_Name(dto.getTutorName());
        Institution institution =  user.getManager().getInstitution();

        Course newCourse = Course.builder()
                .title(dto.getTitle())
                .introduction(dto.getIntroduction())
                //.image(course.getImage())
                .institution(institution)
                .category(dto.getCategory())
                .type(dto.getType())
                .tutor(tutor)
                .build();

        courseRepository.save(newCourse);

        return CourseCreateResponseDTO.builder()
                .msg("코스 생성 성공")
                .build();

    }
    @Transactional
    public CourseModifyResponseDTO modify(Long courseId, CourseModifyRequestDTO dto) {

        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalArgumentException("코스가 존재 하지 않습니다."));


        Course course1 = course.patch(dto);

        courseRepository.save(course1);

        return CourseModifyResponseDTO.builder()
                .msg("수정 되었습니다.")
                .build();

    }

    @Transactional
    public CourseDeleteResponseDTO delete(Long courseId) {

        Course course = courseRepository.findById(courseId).orElseThrow(()-> new IllegalArgumentException("코스가 존재 하지 않습니다."));

        courseRepository.delete(course);

        return CourseDeleteResponseDTO.builder()
                .msg("삭제 되었습니다.")
                .build();
    }
}
