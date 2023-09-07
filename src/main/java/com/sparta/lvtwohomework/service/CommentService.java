package com.sparta.lvtwohomework.service;

import com.sparta.lvtwohomework.dto.CommentRequestDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.Comment;
import com.sparta.lvtwohomework.entity.User;
import com.sparta.lvtwohomework.entity.UserRoleEnum;
import com.sparta.lvtwohomework.repository.BoardRepository;
import com.sparta.lvtwohomework.repository.CommentRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    // 생성
    public CommentResponseDto createComment(Long boardId,
                                            CommentRequestDto requestDto,
                                            HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = boardRepository.findById(boardId)
                .orElseThrow(() -> new IllegalArgumentException("게시글이 존재하지 않습니다."));

        Comment comment = commentRepository.save(new Comment(requestDto, user, board));

        return new CommentResponseDto(comment);
    }

     // 댓글 목록
//    public List<CommentResponseDto> getComments(Long boardId) {
//        return commentRepository.findAllByBoardId(boardId).stream().map(CommentResponseDto::new).toList();
//    }

    public List<CommentResponseDto> getComments(Long boardId) {
        return commentRepository.findAllByBoardIdOrderByCreatedAtDesc(boardId)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());
    }

    // 댓글 선택조회
    public CommentResponseDto selectGetComment(Long id) {
        Comment result = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 댓글 입니다"));
        return new CommentResponseDto(result);
    }

    // 댓글 수정
    @Transactional
    public CommentResponseDto updateComment(Long id,
                                            CommentRequestDto requestDto,
                                            HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Comment comment = commentRepository.findByUsernameAndId(user.getUsername(), id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 사용자 검증 (단, 사용자 Role이 ADMIN과 USER만 있다고 가정함)
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUsername())){
            comment.update(requestDto);
            return new CommentResponseDto(comment);
        } else throw new IllegalArgumentException("수정 권한이 없습니다");
    }

    // 댓글 삭제
    public String deleteComment(Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글이 존재하지 않습니다."));

        // 사용자 검증 (단, 사용자 Role이 ADMIN과 USER만 있다고 가정함)
        if (user.getRole() == UserRoleEnum.ADMIN || user.getUsername().equals(comment.getUsername())) {
            commentRepository.delete(comment);
            return id + "번 댓글을 삭제했습니다.";
        } else {
            throw new IllegalArgumentException("댓글 삭제 권한이 없습니다.");
        }
    }
}


