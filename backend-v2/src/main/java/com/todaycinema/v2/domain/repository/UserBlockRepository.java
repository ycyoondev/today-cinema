package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.UserBlocked;
import com.todaycinema.v2.domain.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserBlockRepository extends JpaRepository<UserBlocked, Long> {
    Optional<UserBlocked> findUserBlockedByFromUserAndToUser(User fromUser, User toUser);
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}
