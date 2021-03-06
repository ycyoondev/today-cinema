package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface MovieRepositoryDataJpa extends JpaRepository<Movie, Long> {
    List<Movie> findTop20ByOrderByTmdbRatingDesc();

    @Query(nativeQuery = true,
            value = "select * from movie m inner join movie_genre mg on m.movie_id = mg.movie_id where mg.genre_id = :genreId LIMIT 20")
    List<Movie> findByGenresInOrderByTmdbRatingDesc(Long genreId);

    @Query(nativeQuery = true,
            value = "select * from movie m where poster_path is NOT NULL order by RAND() limit :num")
    List<Movie> findAllOrderByRand(Integer num);
}