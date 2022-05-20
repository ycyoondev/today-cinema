package com.todaycinema.v2.domain.repository;


import com.todaycinema.v2.domain.User;
import com.todaycinema.v2.domain.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollowing, Long> {
    Optional<UserFollowing> findUserFollowingByFromUserAndToUser(User fromUser, User toUser);
    List<UserFollowing> findUserFollowingsByFromUser(User fromUser);
    List<UserFollowing> findUserFollowingsByToUser(User toUser);
    boolean existsByFromUserAndToUser(User fromUser, User toUser);
}
