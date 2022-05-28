select * from comment;
select * from community_comment;

-- 외래키 무시
SET foreign_key_checks=0;

-- 전체 복사
INSERT INTO community_comment(id, content, created_at, updated_at, review_id, user_id)
select a.comment_id, a.content, a.created_at, a.updated_at, a.review_id, a.user_id
FROM comment a;

INSERT INTO community_review(id, content, user_rating, created_at, updated_at)
select a.review_id, a.content,a.user_rating, a.created_at, a.updated_at
FROM review a;

select * from review;
select * from community_review;

-- movie
select * from movie;
select * from genre;
select * from movie_genre;
select * from movies_movie;
select * from movies_genre;


-- 복사
INSERT INTO movies_genre(id, name, tmdb_id)
select a.genre_id, a.name, a.tmdb_id
FROM genre a;

INSERT INTO movies_movie(movie_id, title, tmdb_rating, overview, tmdb_id, poster_path, genre_id, genre_name)
select a.movie_id, a.title, a.tmdb_rating, a.overview, a.tmdb_id, a.poster_path, b.genre_id, c.name
FROM movie a
inner join movie_genre b
on a.movie_id = b.movie_id
inner join genre c
on b.genre_id = c.genre_id;

select * from movies_movie;

select title, group_concat(genre_name)
from movies_movie
group by title;

INSERT INTO movies_movie(movie_id, title, tmdb_rating, overview, tmdb_id, poster_path, genres)
select a.movie_id, a.title, a.tmdb_rating, a.overview, a.tmdb_id, a.poster_path, group_concat(c.name) as genres
FROM movie a
inner join movie_genre b
on a.movie_id = b.movie_id
inner join genre c
on b.genre_id = c.genre_id
group by a.movie_id;

select * from movies_movie
where movie_id = 201;