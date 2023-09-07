package com.sparta.lvtwohomework.repository;

import com.sparta.lvtwohomework.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByBoardIdOrderByCreatedAtDesc(Long boardId);

    Optional<Comment> findByUsernameAndId(String username, Long id);

}
