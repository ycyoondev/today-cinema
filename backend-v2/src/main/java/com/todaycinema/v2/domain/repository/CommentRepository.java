package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Comment;
import com.todaycinema.v2.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllByReview(Review review);
}