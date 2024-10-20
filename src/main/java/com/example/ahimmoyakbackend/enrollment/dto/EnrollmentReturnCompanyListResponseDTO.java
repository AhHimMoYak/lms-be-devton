package com.example.ahimmoyakbackend.enrollment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentReturnCompanyListResponseDTO {

    private Long companyId;
    private String companyName;

}
