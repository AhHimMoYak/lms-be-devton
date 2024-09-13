package com.example.ahimmoyakbackend.company.service;

import com.example.ahimmoyakbackend.company.dto.CompanyEnrollDepartmentRequestDto;
import com.example.ahimmoyakbackend.company.dto.CompanyEnrollDepartmentResponseDto;
import com.example.ahimmoyakbackend.company.entity.Affiliation;
import com.example.ahimmoyakbackend.company.entity.Company;
import com.example.ahimmoyakbackend.company.entity.Department;
import com.example.ahimmoyakbackend.company.repository.AffiliationRepository;
import com.example.ahimmoyakbackend.company.repository.CompanyRepository;
import com.example.ahimmoyakbackend.company.repository.DepartmentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final AffiliationRepository affiliationRepository;
    private final DepartmentRepository departmentRepository;
    private final CompanyRepository companyRepository;

    @Transactional
    public CompanyEnrollDepartmentResponseDto enroll(Long affiliationId, Long companyId, CompanyEnrollDepartmentRequestDto requestDto) {

        Affiliation affiliation = affiliationRepository.findById(affiliationId).orElseThrow(() -> new IllegalArgumentException("해당 affiliationId 가 없습니다."));
        Company company = companyRepository.findById(companyId).orElseThrow(() -> new IllegalArgumentException("해당 companyId 가 없습니다."));

        Department department = Department.builder()
                .name(requestDto.getDepartmentName())
                .company(company)
                .build();

        departmentRepository.save(department);

        Affiliation updatedAffiliation = Affiliation.builder()
                .id(affiliation.getId())
                .department(department)
                .build();

        affiliationRepository.save(updatedAffiliation);

        return CompanyEnrollDepartmentResponseDto.builder()
                .msg("부서 등록이 완료되었습니다")
                .build();

    }

//    @Transactional
//    public List<CompanyInquiryUserResponseDto> getUserByCompany(Long companyId, Long affiliationId, Long departmentId) {
//        List<Affiliation> affiliations = affiliationRepository.findByCompanyIdAndAffiliationIdAndDepartmentId(companyId, affiliationId, departmentId);
//
//        return affiliations.stream()
//                .map(affiliation -> new CompanyInquiryUserResponseDto(affiliations))
//                .collect(Collectors.toList());
//    }


}
