CREATE DEFINER=`root`@`localhost` TRIGGER `review_AFTER_INSERT` AFTER INSERT ON `review` FOR EACH ROW BEGIN
	insert into community_review (id, content, created_at, updated_at)
	value (NEW.review_id, NEW.content, NEW.created_at, NEW.updated_at);
END