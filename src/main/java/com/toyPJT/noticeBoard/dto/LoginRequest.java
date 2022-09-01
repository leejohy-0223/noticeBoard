package com.toyPJT.noticeBoard.dto;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequest {

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String username;

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String password;
}
