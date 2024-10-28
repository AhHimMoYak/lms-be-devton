package com.example.ahimmoyakbackend.board.controller;

import com.example.ahimmoyakbackend.board.common.BoardType;
import com.example.ahimmoyakbackend.board.dto.BoardCreateRequestDto;
import com.example.ahimmoyakbackend.board.dto.BoardListResponseDto;
import com.example.ahimmoyakbackend.board.dto.BoardPageResponseDto;
import com.example.ahimmoyakbackend.board.dto.CommentWriteRequestDto;
import com.example.ahimmoyakbackend.board.service.CourseBoardService;
import com.example.ahimmoyakbackend.board.service.CourseCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/course/{courseId}/board")
public class CourseBoardController {

    private final CourseBoardService courseBoardService;
    private final CourseCommentService courseCommentService;

    @PostMapping("/{boardType}")
    public ResponseEntity<String> createBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("courseId") long courseId,
            @PathVariable("boardType") String boardType,
            BoardCreateRequestDto requestDto
    ) {
        return courseBoardService.create(userDetails, courseId, BoardType.valueOf(boardType.toUpperCase()), requestDto)
                ? ResponseEntity.ok("게시글 등록 완료")
                : ResponseEntity.badRequest().body("게시글 등록 실패");
    }

    @PatchMapping("/{boardId}")
    public ResponseEntity<String> updateBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("boardId") long boardId,
            BoardCreateRequestDto requestDto
    ) {
        return courseBoardService.update(userDetails, boardId, requestDto)
                ? ResponseEntity.ok("게시글 수정 완료")
                : ResponseEntity.badRequest().body("게시글 수정 실패");
    }

    @DeleteMapping("/{boardId}")
    public ResponseEntity<String> deleteBoard(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("boardId") long boardId
    ) {
        return courseBoardService.delete(userDetails, boardId)
                ? ResponseEntity.ok("게시글 삭제 완료")
                : ResponseEntity.badRequest().body("게시글 삭제 실패");
    }

    @GetMapping("/{boardType}")
    public ResponseEntity<List<BoardListResponseDto>> boardList(
            @PathVariable("courseId") long courseId,
            @PathVariable("boardType") String boardType
    ) {
        return ResponseEntity.ok(courseBoardService.getList(courseId, BoardType.valueOf(boardType.toUpperCase())));
    }

    @GetMapping(value = "/{boardType}", params = "page")
    public ResponseEntity<Page<BoardListResponseDto>> boardList(
            Pageable page,
            @PathVariable("courseId") long courseId,
            @PathVariable String boardType
    ) {
        return ResponseEntity.ok(courseBoardService.getList(courseId, BoardType.valueOf(boardType.toUpperCase()), page));
    }

    @GetMapping("/{boardType}/{boardId}")
    public ResponseEntity<BoardPageResponseDto> getboard(@PathVariable("boardId") long boardId) {
        return ResponseEntity.ok(courseBoardService.getBoard(boardId));
    }

    @PostMapping("/{boardId}/comment")
    public ResponseEntity<String> writeComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("boardId") long boardId,
            CommentWriteRequestDto requestDto
    ) {
        return courseCommentService.write(userDetails, boardId, requestDto)
                ? ResponseEntity.ok("댓글 작성 완료")
                : ResponseEntity.badRequest().body("댓글 작성 실패");
    }

    @PatchMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity<String> editComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("commentId") long commentId,
            CommentWriteRequestDto requestDto
    ) {
        return courseCommentService.edit(userDetails, commentId, requestDto)
                ? ResponseEntity.ok("댓글 수정 완료")
                : ResponseEntity.badRequest().body("댓글 수정 실패");
    }

    @DeleteMapping("/{boardId}/comment/{commentId}")
    public ResponseEntity<String> deleteComment(
            @AuthenticationPrincipal UserDetails userDetails,
            @PathVariable("commentId") long commentId
    ) {
        return courseCommentService.delete(userDetails, commentId)
                ? ResponseEntity.ok("댓글 삭제 완료")
                : ResponseEntity.badRequest().body("댓글 삭제 실패");
    }
}