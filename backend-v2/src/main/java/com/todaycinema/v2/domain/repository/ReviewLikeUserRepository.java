package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Review;
import com.todaycinema.v2.domain.ReviewLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewLikeUserRepository extends JpaRepository<ReviewLikeUser, Long> {
    List<ReviewLikeUser> findAllByReview(Review review);
}