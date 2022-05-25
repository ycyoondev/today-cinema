CREATE DEFINER = CURRENT_USER TRIGGER `todaycinema`.`review_AFTER_INSERT` AFTER INSERT ON `review` FOR EACH ROW
BEGIN
	insert into community_review (id, content, is_spoiler_self, is_spoiler_checked, user_rating, created_at, updated_at, movie_id, user_id)
	value (NEW.review_id, NEW.content, NEW.is_spoiler_self, NEW.is_spoiler_checked, NEW.user_rating, NEW.created_at, NEW.updated_at, NEW.movie_id, NEW.user_id);
END
