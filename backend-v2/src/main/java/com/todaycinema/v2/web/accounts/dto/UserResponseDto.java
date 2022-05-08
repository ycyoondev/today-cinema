package com.todaycinema.v2.web.accounts.dto;

import com.todaycinema.v2.domain.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private String username;

    public static UserResponseDto of(User user) {
        return new UserResponseDto(user.getUsername());
    }
}
