package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Genre;
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
public class GenreRepository {

    private final EntityManager em;

    @Modifying
    @Query(value = "truncate genre", nativeQuery = true)
    public void turncateGenre() {
        log.info("TRUNCATE: GENRE Table");
    }

    public void save(Genre genre) {
        if (genre.getId() == null) {
            em.persist(genre);
        } else {
            em.merge(genre);
        }
    }

    public Genre findOne(Long id) {
        return em.find(Genre.class, id);
    }

    public List<Genre> findAll() {
        return em.createQuery(
                "select g from Genre g", Genre.class
        ).getResultList();
    }
}
