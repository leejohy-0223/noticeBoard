package com.toyPJT.noticeBoard.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.toyPJT.noticeBoard.domain.user.User;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSaveRequest {

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String username;

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String password;

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String email;

    public User toEntity() {
        return User.builder()
            .username(this.username)
            .password(this.password)
            .email(this.email)
            .build();
    }
}
