package com.toyPJT.noticeBoard.dto;

import com.toyPJT.noticeBoard.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {
    private String username;
    private String password;
    private String email;

    public User toEntity() {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .email(this.email)
            .build();
    }
}
