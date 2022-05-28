package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.MovieRecommendUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRecommendUserRepository extends JpaRepository<MovieRecommendUser, Long> {
}