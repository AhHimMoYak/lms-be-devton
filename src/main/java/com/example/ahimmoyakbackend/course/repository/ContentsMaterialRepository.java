package com.example.ahimmoyakbackend.course.repository;

import com.example.ahimmoyakbackend.course.entity.Contents;
import com.example.ahimmoyakbackend.course.entity.ContentsMaterial;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContentsMaterialRepository extends JpaRepository<ContentsMaterial, Long> {
    ContentsMaterial findByContents(Contents contents);

    ContentsMaterial findBySavedName(String savedName);
}