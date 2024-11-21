package com.ssafy.aboha.comment.service;

import com.ssafy.aboha.abog.domain.Abog;
import com.ssafy.aboha.abog.repository.AbogRepository;
import com.ssafy.aboha.comment.domain.Comment;
import com.ssafy.aboha.comment.dto.request.CommentRequest;
import com.ssafy.aboha.comment.repository.CommentRepository;
import com.ssafy.aboha.common.exception.NotFoundException;
import com.ssafy.aboha.user.domain.User;
import com.ssafy.aboha.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        // 관광지 확인
        Abog abog = abogRepository.findByAbogId(request.abogId())
                .orElseThrow(() -> new NotFoundException("해당 아보그가 존재하지 않습니다."));

        // 댓글 생성
        Comment comment = Comment.builder()
                .user(user)
                .abog(abog)
                .content(request.content())
                .build();

        commentRepository.save(comment);

        return comment.getId();
    }
}
