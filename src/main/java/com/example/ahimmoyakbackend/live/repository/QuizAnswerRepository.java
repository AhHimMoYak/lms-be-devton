package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.QuizAnswer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizAnswerRepository extends CrudRepository<QuizAnswer, Long> {
    public List<QuizAnswer> findAllByQuizId(Long quizId);
    public List<QuizAnswer> findAllByLiveId(Long liveId);
}
