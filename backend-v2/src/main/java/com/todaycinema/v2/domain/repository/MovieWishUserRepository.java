package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.MovieWishUser;
import com.todaycinema.v2.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MovieWishUserRepository extends JpaRepository<MovieWishUser, Long> {
    boolean existsByUserAndMovie(User user, Movie movie);
    Optional<MovieWishUser> findMovieWishUserByUserAndMovie(User user, Movie movie);
}