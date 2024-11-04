package com.example.ahimmoyakbackend.live.entity;

import com.example.ahimmoyakbackend.global.entity.Timestamped;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "live_quiz_answer")
public class LiveQuizAnswer extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer answer;

    @ManyToOne
    @JoinColumn(name = "attend_history_id")
    private AttendHistory attendHistory;

    @ManyToOne
    @JoinColumn(name = "live_quiz_id")
    private LiveQuiz liveQuiz;

}