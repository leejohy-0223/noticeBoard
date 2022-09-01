package com.toyPJT.noticeBoard.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class BoardSummaryResponse {
    private String title;
    private String username;
    private int id;
    private int count;
}
