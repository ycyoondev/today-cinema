package com.todaycinema.v2.web.community.service;

import com.todaycinema.v2.domain.Comment;
import com.todaycinema.v2.domain.Review;
import com.todaycinema.v2.domain.repository.CommentRepository;
import com.todaycinema.v2.domain.repository.ReviewRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.accounts.dto.UserMiniDto;
import com.todaycinema.v2.web.community.dto.CommentResponseDto;
import com.todaycinema.v2.web.community.dto.CommentsResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CommentsResponseDto getComments(Long movieId, Long reviewId) {
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        Review review = optionalReview.get();
        List<Comment> comments = commentRepository.findAllByReview(review);
        CommentsResponseDto commentsResponseDto = new CommentsResponseDto();
        for (Comment comment : comments) {
            CommentResponseDto commentResponseDto = new CommentResponseDto(
                    comment.getId(),
                    new UserMiniDto(comment.getUser().getId(), comment.getUser().getUsername()),
                    comment.getContent(),
                    comment.getCreatedAt(),
                    comment.getUpdatedAt()
            );
            commentsResponseDto.getComments().add(commentResponseDto);
        }
        return commentsResponseDto;
    }
}
