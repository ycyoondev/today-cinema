package com.todaycinema.v2.accounts.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserFollowing {

    @Id
    @GeneratedValue
    @Column(name = "user_following_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(name = "from_user_id")
    private User follower; // 팔로잉을 신청하는 유저 (팬, 추종자)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    @Column(name = "to_user_id")
    private User following; // 팔로잉 대상 (연예인, 내가 구독한 사람)
}
