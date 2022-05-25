select * from comment;
select * from community_comment;

-- 외래키 무시
SET foreign_key_checks=0;

-- 전체 복사
INSERT INTO community_comment(id, content, created_at, updated_at, review_id, user_id)
select a.comment_id, a.content, a.created_at, a.updated_at, a.review_id, a.user_id
FROM comment a;

INSERT INTO community_review(id, content, created_at, updated_at)
select a.review_id, a.content, a.created_at, a.updated_at
FROM review a;

select * from review;

-- movie
select * from movie;
select * from genre;
select * from movie_genre;
select * from movies_movie;
select * from movies_genre;
select * from movies_movie_genres;

-- 복사
INSERT INTO movies_genre(id, name, tmdb_id)
select a.genre_id, a.name, a.tmdb_id
FROM genre a;

INSERT INTO movies_movie(id, title, tmdb_rating, overview, tmdb_id)
select a.movie_id, a.title, a.tmdb_rating, a.overview, a.tmdb_id
FROM movie a;

INSERT INTO movies_movie_genres(movie_id, genre_id)
select a.movie_id, a.genre_id
FROM movie_genre a;