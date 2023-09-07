package com.sparta.lvtwohomework.service;

import com.sparta.lvtwohomework.dto.BoardRequestDto;
import com.sparta.lvtwohomework.dto.BoardResponseDto;
import com.sparta.lvtwohomework.dto.CommentResponseDto;
import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.Comment;
import com.sparta.lvtwohomework.entity.User;
import com.sparta.lvtwohomework.jwt.JwtUtil;
import com.sparta.lvtwohomework.repository.BoardRepository;
import com.sparta.lvtwohomework.repository.CommentRepository;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final CommentRepository commentRepository;

    public BoardResponseDto createBoard(BoardRequestDto requestDto, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = new Board(requestDto, user);
        board.setUsername(user.getUsername());


        Board saveBoard = boardRepository.save(board);
        return new BoardResponseDto(saveBoard);
    }

//     게시글 전체 조회시 댓글은 안보임
    public List<BoardResponseDto> getBoard() {
        return boardRepository.findAllByOrderByCreatedAtDesc().stream().map(BoardResponseDto::new).toList();
    }

    // 게시글 조회시 댓글도 보이게
    public List<BoardResponseDto> getBoardWithComments() {
        List<Board> boards = boardRepository.findAll();
        return boards.stream().map(BoardResponseDto::new).toList();
    }

    public BoardResponseDto selectGetBoard(Long id) {
        Board result = boardRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 게시글 입니다"));

        List<CommentResponseDto> comments = commentRepository.findAllByBoardIdOrderByCreatedAtDesc(id)
                .stream()
                .map(CommentResponseDto::new)
                .collect(Collectors.toList());

        return new BoardResponseDto(result, comments);
    }

    public BoardResponseDto updateBoard(Long id,
        BoardRequestDto requestDto,
        HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = boardRepository.findByUsernameAndId(user.getUsername(), id)
            .orElseThrow(()-> new IllegalArgumentException("게시물이 존재하지 않습니다."));

        board.update(requestDto);
        return new BoardResponseDto(board);
    }

    public String deleteBoard(Long id, HttpServletRequest req) {
        User user = (User) req.getAttribute("user");

        Board board = boardRepository.findByUsernameAndId(user.getUsername(), id)
                .orElseThrow(() -> new IllegalArgumentException("작성자만 삭제할 수 있습니다."));

//        // 게시글에 연관된 댓글 가져오기
//        List<Comment> comments = commentRepository.findAllByBoard(board);
//        // 연관된 댓글 삭제
//        commentRepository.deleteAll(comments);
        // 삭제
        boardRepository.delete(board);

        return id + "번 게시물 삭제에 성공했습니다.";
    }
}
