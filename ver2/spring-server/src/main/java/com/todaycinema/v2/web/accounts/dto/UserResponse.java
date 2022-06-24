package com.todaycinema.v2.web.accounts.dto;

import com.todaycinema.v2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String username;

    public static UserResponse of(User user) {
        return new UserResponse(user.getUsername());
    }
}
