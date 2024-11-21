package com.ssafy.aboha.comment.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.comment.domain.Comment;
import com.ssafy.aboha.comment.dto.request.CommentRequest;
import com.ssafy.aboha.comment.dto.response.CommentResponse;
import com.ssafy.aboha.comment.repository.CommentRepository;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final AbogRepository abogRepository;

    /**
     * 댓글 작성
     */
    @Transactional
    public Integer createComment(Integer userId, CommentRequest request) {
        // 사용자 확인
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("로그인한 사용자가 존재하지 않습니다."));

        // 아보그 확인
        Abog abog = abogRepository.findByAbogId(request.abogId())
                .orElseThrow(() -> new NotFoundException("아보그가 존재하지 않습니다."));

        // 댓글 생성
        Comment comment = Comment.builder()
                .user(user)
                .abog(abog)
                .content(request.content())
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }

    /**
     * 댓글 목록 조회
     */
    public List<CommentResponse> getComments(Integer abogId) {
        Abog abog = abogRepository.findByAbogId(abogId)
                .orElseThrow(() -> new NotFoundException("아보그가 존재하지 않습니다."));

        List<Comment> comments = commentRepository.findByAbog(abog);
        return comments.stream()
                .map(CommentResponse::from)
                .toList();
    }

    /**
     * 댓글 상세 조회
     */
    public CommentResponse getComment(Integer id) {
        // 댓글 확인
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("댓글이 존재하지 않습니다."));

        return CommentResponse.from(comment);
    }
}
