package com.toyPJT.noticeBoard.exception;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = GlobalException.class)
    private ResponseEntity<String> handleException(GlobalException exception) {
        return exception.getResponse();
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    private ResponseEntity<String> handleException() {
        return ExceptionType.DUPLICATE_ID.getResponse();
    }
}
