CREATE DEFINER=`root`@`localhost` TRIGGER `review_AFTER_UPDATE` AFTER UPDATE ON `review` FOR EACH ROW BEGIN
	update community_review set 
		content = NEW.content, 
        user_rating = NEW.user_rating,
        created_at = NEW.created_at, 
        updated_at = NEW.updated_at,
        movie_id = NEW.movie_id
	where id = NEW.review_id;
END