package com.toyPJT.noticeBoard.exception;

import org.springframework.http.ResponseEntity;

public class GlobalException extends RuntimeException {

    private final ExceptionType exceptionType;

    public GlobalException(ExceptionType exceptionType) {
        this.exceptionType = exceptionType;
    }

    public ResponseEntity<String> getResponse() {
        return exceptionType.getResponse();
    }
}
