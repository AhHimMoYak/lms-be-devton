package com.example.ahimmoyakbackend.live.entity;

import com.example.ahimmoyakbackend.live.common.LiveState;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@RedisHash(value = "live")
public class LiveStatus {
    @Id
    private long id;
    private LiveState status;
}
