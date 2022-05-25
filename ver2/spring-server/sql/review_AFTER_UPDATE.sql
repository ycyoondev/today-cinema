CREATE DEFINER = CURRENT_USER TRIGGER `todaycinema`.`review_AFTER_UPDATE` AFTER UPDATE ON `review` FOR EACH ROW
BEGIN
	update community_review set 
		content = NEW.content, 
        is_spoiler_self = NEW.is_spoiler_self, 
        is_spoiler_checked = NEW.is_spoiler_checked, 
        user_rating = NEW.user_rating, 
        created_at = NEW.created_at, 
        updated_at = NEW.updated_at, 
        movie_id = NEW.movie_id, 
        user_id = NEW.user_id
	where id = NEW.review_id;
END