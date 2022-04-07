package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class MovieRepository {

    private final EntityManager em;

    public void save(Movie movie) {
        if (movie.getId() == null) {
            em.persist(movie);
        } else {
            em.merge(movie);
        }
    }

    public Movie findOne(Long id) {
        return em.find(Movie.class, id);
    }

    public List<Movie> findAll() {
        return em.createQuery(
                "select m from Movie m", Movie.class
        ).getResultList();
    }

    public List<Movie> findTopNumByTmdbRating(int num) {
        return em.createQuery(
                "select m from Movie m " +
                        "order by m.tmdbRating desc", Movie.class
        ).setMaxResults(num).getResultList();
    }

    @Modifying
    @Query(value = "truncate movie", nativeQuery = true)
    public void truncateMovie() {
        log.info("TRUNCATE: MOVIE Table");
    }
}
