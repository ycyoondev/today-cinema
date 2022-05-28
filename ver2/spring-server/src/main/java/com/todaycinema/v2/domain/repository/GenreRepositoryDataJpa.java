package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Genre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GenreRepositoryDataJpa extends JpaRepository<Genre, Long> {
    @Query(nativeQuery = true,
            value = "select distinct g.genre_id from movie_genre mg " +
                    "inner join genre g on g.genre_id = mg.genre_id;")
    List<Long> findAllId();
}