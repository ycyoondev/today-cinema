package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findAllByMovie(Movie movie);
}