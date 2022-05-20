package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Review;
import com.todaycinema.v2.domain.ReviewSpoilerCheckUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewSpoilerCheckUserRepository extends JpaRepository<ReviewSpoilerCheckUser, Long> {
    List<ReviewSpoilerCheckUser> findAllByReview(Review review);
}