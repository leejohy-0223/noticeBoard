package com.toyPJT.noticeBoard.dto;

import javax.validation.constraints.NotBlank;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReplySaveRequest {

    @NotBlank(message = "필드에 공백이 존재합니다.")
    private String content;
}
