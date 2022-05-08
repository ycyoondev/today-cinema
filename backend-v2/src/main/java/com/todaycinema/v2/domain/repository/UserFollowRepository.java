package com.todaycinema.v2.domain.repository;


import com.todaycinema.v2.domain.UserFollowing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserFollowRepository extends JpaRepository<UserFollowing, Long> {
}
