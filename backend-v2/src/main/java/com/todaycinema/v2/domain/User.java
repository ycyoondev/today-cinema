package com.todaycinema.v2.domain;

import com.todaycinema.v2.domain.account.Authority;
import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
public class User {

    @Id @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String username;

    private String password;

    private String introduction;

//    @Column(name = "is_superuser")
//    private boolean isSuperuser;

    @Enumerated(EnumType.STRING)
    private Authority authority;

    @Builder
    public User(String username, String password, Authority authority) {
        this.username = username;
        this.password = password;
        this.authority = authority;
    }
}
