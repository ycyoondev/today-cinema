CREATE DEFINER=`root`@`localhost` TRIGGER `review_AFTER_INSERT` AFTER INSERT ON `review` FOR EACH ROW BEGIN
	insert into community_review (id, content, user_rating, created_at, updated_at)
	value (NEW.review_id, NEW.content, NEW.user_rating, NEW.created_at, NEW.updated_at);
END