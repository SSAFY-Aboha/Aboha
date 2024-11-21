package com.ssafy.aboha.comment.repository;

import com.ssafy.aboha.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
}
