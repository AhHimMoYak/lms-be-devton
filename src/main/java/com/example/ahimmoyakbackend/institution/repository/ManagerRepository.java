package com.example.ahimmoyakbackend.institution.repository;

import com.example.ahimmoyakbackend.institution.entity.Institution;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.institution.entity.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ManagerRepository extends JpaRepository<Manager, Long> {

    Optional<Manager> findByUser_Username(String username);
    Manager findByUser(User user);
    Manager findByInstitution(Institution institution);
}