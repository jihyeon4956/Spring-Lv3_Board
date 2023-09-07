package com.sparta.lvtwohomework.controller;

import com.sparta.lvtwohomework.dto.CommentRequestDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
import com.sparta.lvtwohomework.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성 API
    @PostMapping("/board/{boardId}/comment")
    public CommentResponseDto createComment(@PathVariable Long boardId,
                                            @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest req) {
        return commentService.createComment(boardId, requestDto, req);
    }

    // 게시판 기준 전체 댓글 목록 조회하기 API
    @GetMapping("board/{boardId}/comments")
    public List<CommentResponseDto> getComments(@PathVariable Long boardId) {
        return commentService.getComments(boardId);
    }

    // 선택한 댓글 조회 API
    @GetMapping("board/{boardId}/comment/{id}")
    public CommentResponseDto selectGetComment(@PathVariable Long id)  {
        return commentService.selectGetComment(id);
    }

    // 선택한 댓글 수정 API
    @PutMapping("board/{boardId}/comment/{id}")
    public CommentResponseDto updateComment(@PathVariable Long id,
                                            @RequestBody CommentRequestDto requestDto,
                                            HttpServletRequest req) {
        return commentService.updateComment(id, requestDto, req);
    }

    // 선택한 댓글 삭제 API
    @DeleteMapping("/comment/{id}")
    public ResponseEntity<String> deleteComment(@PathVariable Long id, HttpServletRequest req) {
        String message = commentService.deleteComment(id, req);
        return ResponseEntity.ok(message);
    }
}
