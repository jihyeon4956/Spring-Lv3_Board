package com.sparta.lvtwohomework.dto;

import com.sparta.lvtwohomework.entity.Comment;
import lombok.Getter;

@Getter
public class CommentResponseDto {
    private Long id;
    private String comment;
    private String username;


    public CommentResponseDto(Comment comment) {
        this.id = comment.getId();
        this.username = comment.getUsername();
        this.comment = comment.getComment();
    }

    public CommentResponseDto(String comment, String username, Long id) {
        this.id = id;
        this.username = username;
        this.comment = comment;
    }
}
