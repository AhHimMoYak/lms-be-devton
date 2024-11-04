package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.repository.UserRepository;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.course.entity.Course;
import com.example.ahimmoyakbackend.course.repository.CourseRepository;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import com.example.ahimmoyakbackend.live.common.LiveState;
import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.entity.LiveStatus;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.repository.LiveStatusRepository;
import com.example.ahimmoyakbackend.live.repository.LiveStreamingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class LiveService {
    private final LiveStreamingRepository liveStreamingRepository;
    private final CourseRepository courseRepository;
    private final UserService userService;
    private final UserRepository userRepository;
    private final LiveStatusRepository liveStatusRepository;
    private final ChatService chatService;

    @Transactional
    public boolean createLive(LiveCreateRequestDTO requestDTO, Long courseId, UserDetails userDetails) {
        User user = userService.getAuth(userDetails);
        Optional<Course> optionalCourse = courseRepository.findById(courseId);
        if(optionalCourse.isEmpty()){
            throw new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다.");
        }
        Course course = optionalCourse.get();
        if(!course.getTutor().equals(user)){
            throw new ApiException(HttpStatus.UNAUTHORIZED, "강사만 라이브를 생성할 수 있습니다.");
        }
        liveStreamingRepository.save(requestDTO.toEntity(course));
        return true;
    }

    public LiveCourseResponseDTO getLive(Long liveId) {
        return liveStreamingRepository.findById(liveId)
                .map(ls -> LiveCourseResponseDTO.from(ls, ls.getCourse()))
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "라이브 정보를 찾을 수 없습니다."));
    }

    public List<LiveCourseResponseDTO> getLiveListByCourse(Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "코스를 찾을 수 없습니다."));
        return liveStreamingRepository.findByCourse(course).stream()
                .map(entity -> LiveCourseResponseDTO.from(entity, course))
                .toList();

    }

    public List<LiveTutorResponseDTO> getLiveListByTutor(UserDetails userDetails) {
        User tutor = userService.getAuth(userDetails);
        return liveStreamingRepository.findByCourse_Tutor(tutor).stream()
                .map(entity -> LiveTutorResponseDTO.from(entity, tutor))
                .toList();
    }

    @Transactional
    public boolean publishLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        Long tutorId = Long.parseLong(keyAndTutor[1]);
        LiveStreaming liveStreaming = liveStreamingRepository.findById(liveId).orElse(null);
        User tutor = userRepository.findById(tutorId).orElse(null);
        if(liveStreaming != null && tutor != null){
            if(liveStreaming.getCourse().getTutor().equals(tutor)){
                liveStreaming.setState(LiveState.ON);
                liveStatusRepository.save(new LiveStatus(liveId, LiveState.ON));
                log.info("Publish live streaming success. stream_key: {}, tutor_id: {}", liveId, tutorId);
                return true;
            }
        }
        log.error("Publish live streaming failed. stream_key: {}, tutor_id: {}", liveId, tutorId);
        return false;
    }

    @Transactional
    public void endLive(String streamKey) {
        String[] keyAndTutor = streamKey.split("_");
        Long liveId = Long.parseLong(keyAndTutor[0]);
        liveStreamingRepository.findById(liveId).ifPresent(liveStreaming -> liveStreaming.setState(LiveState.END));
        liveStatusRepository.deleteById(liveId);
        chatService.deleteAll(liveId);
        log.info("Close live streaming. stream_key: {}, tutor_id: {}", liveId, keyAndTutor[1]);
    }
}
