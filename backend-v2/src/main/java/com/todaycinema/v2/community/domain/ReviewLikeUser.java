package com.todaycinema.v2.community.domain;

import com.todaycinema.v2.accounts.domain.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class ReviewLikeUser {

    @Id
    @GeneratedValue
    @Column(name = "review_like_user_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "review_id")
    private Review review;
}
