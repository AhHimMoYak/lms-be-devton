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
@Table(name = "live_quiz")
public class LiveQuiz extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String question;

    @Column(nullable = false)
    private Integer answer;

    @Column
    private String solution;

    @ManyToOne
    @JoinColumn(name = "live_id")
    private LiveStreaming liveStreaming;

}