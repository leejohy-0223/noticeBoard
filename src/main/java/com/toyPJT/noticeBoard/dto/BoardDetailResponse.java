package com.toyPJT.noticeBoard.dto;

import java.util.List;
import java.util.stream.Collectors;

import com.toyPJT.noticeBoard.domain.reply.Reply;

import lombok.Getter;

@Getter
public class BoardDetailResponse {
    private String title;
    private String username;
    private int id;
    private int count;
    private String content;
    private List<ReplyResponse> replies;

    public BoardDetailResponse(String title, String username, int id, int count, String content, List<Reply> replies) {
        this.title = title;
        this.username = username;
        this.id = id;
        this.count = count;
        this.content = content;
        this.replies = replies.stream()
            .map(reply -> new ReplyResponse(reply.getId(), reply.getContent(), reply.findUsername()))
            .collect(Collectors.toList());
    }
}
