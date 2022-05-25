CREATE DEFINER = CURRENT_USER TRIGGER `todaycinema`.`review_AFTER_DELETE` AFTER DELETE ON `review` FOR EACH ROW
BEGIN
	delete from community_review
    where id = OLD.review_id;
END
