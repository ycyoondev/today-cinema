select * from comment;
select * from community_comment;

-- 외래키 무시
SET foreign_key_checks=0;

-- 전체 복사
INSERT INTO community_comment(id, content, created_at, updated_at, review_id, user_id)
select a.comment_id, a.content, a.created_at, a.updated_at, a.review_id, a.user_id
FROM comment a;