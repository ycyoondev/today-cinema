package com.todaycinema.v2.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class UserBlocked {

    @Id
    @GeneratedValue
    @Column(name = "user_blocked_id")
    private Long id;

    @JoinColumn(name = "from_user_id")
    @ManyToOne
    private User fromUser;

    @JoinColumn(name = "to_user_id")
    @ManyToOne
    private User toUser;

    @Builder
    public UserBlocked(User fromUser, User toUser) {
        this.fromUser = fromUser;
        this.toUser = toUser;
    }
}
