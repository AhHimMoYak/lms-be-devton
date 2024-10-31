package com.example.ahimmoyakbackend.live.controller;

import com.example.ahimmoyakbackend.live.dto.LiveCourseResponseDTO;
import com.example.ahimmoyakbackend.live.dto.LiveCreateRequestDTO;
import com.example.ahimmoyakbackend.live.dto.LivePublishFormDTO;
import com.example.ahimmoyakbackend.live.dto.LiveTutorResponseDTO;
import com.example.ahimmoyakbackend.live.service.LiveService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/live")
@RequiredArgsConstructor
public class LiveController {

    private final LiveService liveService;

    @PostMapping
    public ResponseEntity<Void> createLive(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId, @RequestBody LiveCreateRequestDTO requestDTO) {
        boolean result = liveService.createLive(requestDTO, courseProvideId, userDetails);
        return result ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @GetMapping("/{liveId}")
    public ResponseEntity<LiveCourseResponseDTO> getLive(@AuthenticationPrincipal UserDetails userDetails, @PathVariable Long liveId) {
        return ResponseEntity.ok(liveService.getLive(liveId));
    }

    @GetMapping
    public ResponseEntity<List<LiveCourseResponseDTO>> getCourseLives(@AuthenticationPrincipal UserDetails userDetails, @RequestParam Long courseProvideId) {
        return ResponseEntity.ok().body(liveService.getLiveListByCourse(courseProvideId));
    }

    @GetMapping("/instructor")
    public ResponseEntity<List<LiveTutorResponseDTO>> getTutorLives(@AuthenticationPrincipal UserDetails userDetails) {
        return ResponseEntity.ok().body(liveService.getLiveListByTutor(userDetails));
    }

    @PostMapping("/publish")
    public ResponseEntity<Void> publishLive(@ModelAttribute LivePublishFormDTO form) {
        return liveService.publishLive(form.getName()) ? ResponseEntity.ok().build() : ResponseEntity.badRequest().build();
    }

    @PostMapping("/publish-done")
    public ResponseEntity<Void> publishLiveDone(@ModelAttribute LivePublishFormDTO form) {
        liveService.endLive(form.getName());
        return ResponseEntity.ok().build();
    }
}