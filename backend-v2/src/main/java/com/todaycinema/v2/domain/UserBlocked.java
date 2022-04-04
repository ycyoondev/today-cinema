package com.todaycinema.v2.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
public class UserBlocked {

    @Id
    @GeneratedValue
    @Column(name = "user_blocked_id")
    private Long id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    @Column(name = "from_user_id")
//    private User blocked;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "user_id")
//    @Column(name = "to_user_id")
//    private User follower;
}
