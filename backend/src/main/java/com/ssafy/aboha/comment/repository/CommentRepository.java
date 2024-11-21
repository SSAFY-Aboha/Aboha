package com.ssafy.aboha.comment.repository;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {

    List<Comment> findByAbog(Abog abog);
}
