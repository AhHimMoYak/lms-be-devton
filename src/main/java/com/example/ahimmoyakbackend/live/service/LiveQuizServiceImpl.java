package com.example.ahimmoyakbackend.live.service;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.auth.service.UserService;
import com.example.ahimmoyakbackend.global.exception.ApiException;
import com.example.ahimmoyakbackend.live.dto.*;
import com.example.ahimmoyakbackend.live.entity.LiveQuiz;
import com.example.ahimmoyakbackend.live.entity.LiveStreaming;
import com.example.ahimmoyakbackend.live.entity.QuizAnswer;
import com.example.ahimmoyakbackend.live.entity.QuizStatus;
import com.example.ahimmoyakbackend.live.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LiveQuizServiceImpl implements LiveQuizService {

    private final UserService userService;
    private final LiveStreamingRepository liveStreamingRepository;
    private final LiveQuizRepository liveQuizRepository;
    private final LiveQuizOptionRepository liveQuizOptionRepository;
    private final QuizStatusRepository quizStatusRepository;
    private final QuizAnswerRepository quizAnswerRepository;

    @Override
    @Transactional
    public boolean create(UserDetails userDetails, long liveId, LiveQuizCreateRequestDto requestDto) {
        User user = userService.getAuth(userDetails);
        LiveStreaming live = liveStreamingRepository.findById(liveId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "라이브를 찾을 수 없습니다."));
        if(!live.getCourse().getTutor().equals(user)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "강사만 라이브에 퀴즈를 만들 수 있습니다.");
        }
        try {
            LiveQuiz liveQuiz = liveQuizRepository.save(requestDto.toEntity(live));
            liveQuizOptionRepository.saveAll(requestDto.options().stream()
                    .map(dto -> dto.toEntity(liveQuiz)).toList());
        } catch (Exception e) {
            throw new ApiException(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error");
        }
        return true;
    }

    @Override
    public List<LiveQuizResponseDto> getList(long liveId) {
        LiveStreaming live = liveStreamingRepository.findById(liveId).orElseThrow(() -> new ApiException(HttpStatus.NOT_FOUND, "라이브를 찾을 수 없습니다."));
        return liveQuizRepository.findByLiveStreaming(live).stream()
                .map(quiz -> LiveQuizResponseDto
                        .from(quiz, quiz.getLiveQuizOptions().stream()
                                .map(LiveQuizOptionDto::from).toList())).toList();
    }

    @Override
    @Transactional
    public LiveQuizResponseDto send(long liveId, long quizId) {
        LiveQuiz quiz = liveQuizRepository.findById(quizId).orElse(null);
        if(quiz == null) {
            return null;
        }
        quizStatusRepository.save(QuizStatus.builder()
                .id(quizId)
                .liveId(liveId)
                .options(quiz.getLiveQuizOptions().stream()
                        .collect(Collectors.toMap(opt -> opt.getIdx(), opt -> 0))).build());
        return LiveQuizResponseDto.from(quiz, quiz.getLiveQuizOptions().stream()
                .map(LiveQuizOptionDto::from).toList());
    }

    @Override
    @Transactional
    public QuizStatusSubDto answer(long liveId, QuizAnswerMsgDto pubDto) {
        QuizStatus quizStatus = quizStatusRepository.findById(pubDto.quizId()).orElse(null);
        if(quizStatus == null) {
            return null;
        }
        quizStatus.getOptions().put(pubDto.answer(), quizStatus.getOptions().get(pubDto.answer()) + 1);
        quizStatusRepository.save(quizStatus);
        quizAnswerRepository.save(QuizAnswer.builder()
                .quizId(pubDto.quizId())
                .liveId(liveId)
                .answer(pubDto.answer())
                .build());
        return null;
    }

}
