package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.ReviewLikeUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewLikeUserRepository extends JpaRepository<ReviewLikeUser, Long> {
}