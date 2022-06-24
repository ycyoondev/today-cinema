package com.todaycinema.v2.web.community.service;

import com.todaycinema.v2.domain.Comment;
import com.todaycinema.v2.domain.Review;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.CommentRepository;
import com.todaycinema.v2.domain.repository.ReviewRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import com.todaycinema.v2.web.community.dto.CommentRequest;
import com.todaycinema.v2.web.community.dto.CommentResponse;
import com.todaycinema.v2.web.community.dto.CommentsResponse;
import com.todaycinema.v2.web.community.dto.MessageResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
@Service
public class CommentService {
    private final ReviewRepository reviewRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    public CommentsResponse getComments(Long movieId, Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.get();
        List<Comment> comments = commentRepository.findAllByReview(review);
        CommentsResponse commentsResponse = new CommentsResponse();
        for (Comment comment : comments) {
            CommentResponse commentResponse = new CommentResponse(
                    comment.getId(),
                    new UserMiniDto(comment.getUser().getId(), comment.getUser().getUsername()),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
            commentsResponse.getComments().add(commentResponse);
        }
        return commentsResponse;
    }

    @Transactional
    public CommentResponse createComment(Long movieId, Long reviewId, Authentication authentication, CommentRequest commentRequest) {
        // 저장
        User user = userRepository.findByUsername(authentication.getName()).get();
        Review review = reviewRepository.findById(reviewId).get();
        Comment comment = new Comment(
                commentRequest.getContent(),
                LocalDateTime.now(),
                LocalDateTime.now(),
                user,
                review);
        Comment savedComment = commentRepository.save(comment);
        // response
        return new CommentResponse(
                savedComment.getId(),
                new UserMiniDto(user.getId(), user.getUsername()),
                savedComment.getContent(),
                savedComment.getCreatedAt(),
                savedComment.getUpdatedAt());
    }

    @Transactional
    public MessageResponse deleteComment(Long movieId, Long reviewId, Long commentId) {
        commentRepository.deleteById(commentId);
        return new MessageResponse("comment delete");
    }
}
