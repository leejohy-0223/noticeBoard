package com.toyPJT.noticeBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ReplyResponse {
    private int id;
    private String content;
    private String username;
}
