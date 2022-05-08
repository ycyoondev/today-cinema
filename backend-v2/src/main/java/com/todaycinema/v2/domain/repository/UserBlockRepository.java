package com.todaycinema.v2.domain.repository;

import com.todaycinema.v2.domain.UserBlocked;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserBlockRepository extends JpaRepository<UserBlocked, Long> {
}
