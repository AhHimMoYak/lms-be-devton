package com.example.ahimmoyakbackend.global.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}