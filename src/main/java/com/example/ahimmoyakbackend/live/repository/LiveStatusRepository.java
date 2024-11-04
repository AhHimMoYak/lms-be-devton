package com.example.ahimmoyakbackend.live.repository;

import com.example.ahimmoyakbackend.live.entity.LiveStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LiveStatusRepository extends CrudRepository<LiveStatus, Long> {
}
