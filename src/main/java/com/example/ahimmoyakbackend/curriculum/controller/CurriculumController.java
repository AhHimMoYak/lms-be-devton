package com.example.ahimmoyakbackend.curriculum.controller;

import com.example.ahimmoyakbackend.auth.config.security.UserDetailsImpl;
import com.example.ahimmoyakbackend.course.dto.CurriculumCreateRequestDTO;
import com.example.ahimmoyakbackend.course.dto.CurriculumResponseDTO;
import com.example.ahimmoyakbackend.curriculum.dto.CurriculumChangeOrderResponseDTO;
import com.example.ahimmoyakbackend.curriculum.service.CurriculumService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1")
public class CurriculumController {

    private final CurriculumService curriculumService;

    @PatchMapping("curriculum/changeOrder")
    public ResponseEntity<CurriculumChangeOrderResponseDTO> curriculumChangeOrder(
            @RequestParam Long courseId,
            @RequestParam Long target1,
            @RequestParam Long target2
    ) {
        CurriculumChangeOrderResponseDTO responseDTO = curriculumService.changeOrder(courseId, target1, target2);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);


    }

    // 커리큘럼 생성
    @PostMapping
    public ResponseEntity<CurriculumResponseDTO> createCurriculum(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long courseId,
            @Valid @RequestParam CurriculumCreateRequestDTO requestDTO
    ) {
        curriculumService.create(userDetails.getUser(), courseId, requestDTO);
        CurriculumResponseDTO response = CurriculumResponseDTO.builder()
                .msg("생성되었습니다.")
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // 커리큘럼 제거
    @DeleteMapping
    public ResponseEntity<CurriculumResponseDTO> deleteCurriculum(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long courseId,
            @RequestParam Long curriculumId
    ) {
        if (userDetails == null) {
            throw new IllegalArgumentException("사용자가 인증되지 않았습니다.");
        }

        CurriculumResponseDTO responseDTO = curriculumService.delete(userDetails.getUser(), courseId, curriculumId);

        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }

    // 커리큘럼 수정
    @PatchMapping
    public ResponseEntity<CurriculumResponseDTO> updateCurriculum(
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestParam Long courseId,
            @RequestParam Long curriculumId,
            @RequestParam CurriculumCreateRequestDTO requestDto
    ) {
        CurriculumResponseDTO responseDTO = curriculumService.modifyCurriculum(userDetails.getUser(), courseId, curriculumId, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(responseDTO);
    }
}
