package com.ssafy.aboha.comment.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.comment.domain.Comment;
import com.ssafy.aboha.comment.dto.request.CommentRequest;
import com.ssafy.aboha.comment.dto.request.CommentUpdateRequest;
import com.ssafy.aboha.comment.dto.response.CommentResponse;
import com.ssafy.aboha.comment.repository.CommentRepository;
import com.ssafy.aboha.common.exception.ForbiddenException;
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

        // 댓글수 증가
        abog.increaseCommentCount();

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

    /**
     * 댓글 수정
     */
    @Transactional
    public void updateComment(Integer userId, Integer id, CommentUpdateRequest request) {
        // 1. 리뷰 조회
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 2. 사용자 권한 확인
        if(!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("댓글 수정 권한이 없습니다.");
        }

        // 3. 댓글 정보 업데이트
        comment.updateContent(request.content());
    }

    /**
     * 댓글 삭제
     */
    @Transactional
    public void deleteComment(Integer userId, Integer id) {
        // 1. 리뷰 조회
        Comment comment = commentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("댓글을 찾을 수 없습니다."));

        // 2. 사용자 권한 확인
        if(!comment.getUser().getId().equals(userId)) {
            throw new ForbiddenException("댓글 삭제 권한이 없습니다.");
        }

        // 3. 아보그 정보 업데이트
        Abog abog = comment.getAbog();
        abog.decreaseCommentCount();

        // 3. 댓글 삭제
        commentRepository.delete(comment);
    }

}
