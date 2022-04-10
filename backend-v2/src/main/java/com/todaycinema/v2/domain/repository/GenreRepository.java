package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.Genre;
import com.todaycinema.v2.domain.Movie;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class GenreRepository {

    private final EntityManager em;

    @Modifying
    @Query(value = "truncate genre", nativeQuery = true)
    public void truncateGenre() {
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

    public String findGenreName(Long genreId) {
        try {
            return em.createQuery(
                    "select g from Genre g " +
                            "where g.tmdbId=:genreId", Genre.class
            ).setParameter("genreId", genreId).getSingleResult().getName();
        } catch (NullPointerException e) {
            return "없는장르";
        }
        
    }

    // 장르 아이디를 받아, 장르 리스트를 반환
    public List<Genre> makeGenreList(Integer[] ids) {
        List<Genre> result = new ArrayList<>();
        for (Integer id : ids) {
            Long genreId = Long.valueOf(id);
            Genre genre = findOne(genreId);
            result.add(genre);
        }
        return result;
    }
}
