package com.sparta.lvtwohomework.entity;

import com.sparta.lvtwohomework.dto.CommentRequestDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class Comment extends Timestamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String username;
    @Column(nullable = false)
    private String comment;


    //    @ManyToOne(cascade = CascadeType.ALL)
    @ManyToOne
    @JoinColumn(name = "board_id")
    private Board board;


    public Comment(CommentRequestDto requestDto, User user, Board board) {
        this.username = user.getUsername();
        this.comment = requestDto.getComment();
        this.board = board;
    }

    public void update(CommentRequestDto requestDto) {
        this.comment = requestDto.getComment();
    }
}

