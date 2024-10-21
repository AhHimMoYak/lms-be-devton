package com.example.ahimmoyakbackend.company.repository;

import com.example.ahimmoyakbackend.auth.entity.User;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AffiliationRepository extends JpaRepository<Affiliation, Long> {
    List<Affiliation> findAllByDepartment_Company_Id(Long id);

    Affiliation findByUser(User user);
}