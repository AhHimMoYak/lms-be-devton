package com.example.ahimmoyakbackend.live.entity;

import com.example.ahimmoyakbackend.course.entity.Enrollment;
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
@Table(name = "attend_history")
public class AttendHistory extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Integer rate;   // 참여한 총 시간 (redis 에서 기록 후 종합해서 저장될 예정)

//    @Column(columnDefinition = "tinyint(1) default 0")
    @Column
    private Boolean attendance;

    @ManyToOne
    @JoinColumn(name = "enrollment_id")
    private Enrollment enrollment;

    @ManyToOne
    @JoinColumn(name = "live_streaming_id")
    private LiveStreaming liveStreaming;

}