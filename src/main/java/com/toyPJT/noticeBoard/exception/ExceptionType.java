package com.toyPJT.noticeBoard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ExceptionType {

    USER_NAME_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 유저 이름입니다."),
    USER_ID_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 유저 아이디입니다."),
    BOARD_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 게시글 아이디입니다."),
    REPLY_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 댓글 아이디입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 등록된 유저 아이디입니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.CONFLICT, "유저 아이디 혹은 비밀번호가 일치하지 않습니다."),
    NOT_A_WRITER(HttpStatus.BAD_REQUEST, "작성자만 삭제 혹은 수정할 수 있습니다."),
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 에러 발생");

    private final HttpStatus httpStatus;
    private final String message;

    ExceptionType(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public ResponseEntity<String> getResponse() {
        return new ResponseEntity<>(message, httpStatus);
    }
}
