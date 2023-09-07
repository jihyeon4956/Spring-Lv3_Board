package com.sparta.lvtwohomework.dto;

import com.sparta.lvtwohomework.entity.Board;
import com.sparta.lvtwohomework.entity.Comment;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class BoardResponseDto {
    private Long id;
    private String username;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private List<CommentResponseDto> commentList = new ArrayList<>();


    public BoardResponseDto(Board board) {
        this.id = board.getId();
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        board.getCommentList().forEach(comment -> commentList.add(new CommentResponseDto(comment)));
        Collections.reverse(commentList);
    }

    public BoardResponseDto(Board board, List<CommentResponseDto> comments) {
        this.id = board.getId();
        this.username = board.getUsername();
        this.title = board.getTitle();
        this.contents = board.getContents();
        this.createdAt = board.getCreatedAt();
        this.commentList = comments;
    }
}
