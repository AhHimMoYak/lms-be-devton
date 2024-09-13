package com.example.ahimmoyakbackend.board.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "BoardController")
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CourseBoardController {
}
