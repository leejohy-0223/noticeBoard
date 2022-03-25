package com.toyPJT.noticeBoard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public enum ExceptionType {

    ID_DOES_NOT_EXIST(HttpStatus.NOT_FOUND, "등록되지 않은 유저 아이디입니다."),
    DUPLICATE_ID(HttpStatus.CONFLICT, "이미 등록된 유저 아이디입니다."),
    PASSWORDS_DO_NOT_MATCH(HttpStatus.CONFLICT, "유저 아이디 혹은 비밀번호가 일치하지 않습니다.");

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
