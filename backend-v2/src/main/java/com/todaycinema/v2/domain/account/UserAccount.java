package com.todaycinema.v2.domain.account;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.security.core.userdetails.User;

import java.util.Collections;

@Getter
@EqualsAndHashCode()
public class UserAccount extends User {

    private com.todaycinema.v2.domain.User user;

    public UserAccount(com.todaycinema.v2.domain.User user) {
        super(user.getUsername(), user.getPassword(), Collections.singletonList(newSimpleGrantedAuthority(user.getAuthorities())));
        this.user = user;
    }
}
