package com.todaycinema.v2.web.movies.service;

import com.todaycinema.v2.domain.Movie;
import com.todaycinema.v2.domain.MovieWishUser;
import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.repository.MovieRepository;
import com.todaycinema.v2.domain.repository.MovieWishUserRepository;
import com.todaycinema.v2.domain.repository.UserRepository;
import com.todaycinema.v2.web.movies.dto.MovieWishResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MovieUserService {
    private final MovieRepository movieRepository;
    private final UserRepository userRepository;
    private final MovieWishUserRepository movieWishUserRepository;

    @Transactional
    public MovieWishResponse postWishMovie(long movieId, Authentication authentication) {
        User user = userRepository.findByUsername(authentication.getName()).get();
        Movie movie = movieRepository.findOne(movieId);
        if (movie == null) {
            return new MovieWishResponse("없는 영화입니다.");
        }
        Optional<MovieWishUser> movieWishUserOptional = movieWishUserRepository.findMovieWishUserByUserAndMovie(user, movie);
        if (movieWishUserOptional.isPresent()) {
            movieWishUserRepository.delete(movieWishUserOptional.get());
            return new MovieWishResponse("북마크 제거에 성공했습니다.");
        } else {
            MovieWishUser movieWishUser = new MovieWishUser(user, movie);
            movieWishUserRepository.save(movieWishUser);
            return new MovieWishResponse("북마크 저장에 성공했습니다.");
        }
    }
}
