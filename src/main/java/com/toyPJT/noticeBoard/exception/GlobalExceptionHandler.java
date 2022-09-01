package com.toyPJT.noticeBoard.exception;

import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import java.util.Objects;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = GlobalException.class)
    private ResponseEntity<String> handleException(GlobalException exception) {
        log.warn("GlobalException 발생", exception);
        return exception.getResponse();
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    private ResponseEntity<String> handleException(MethodArgumentNotValidException exception) {
        log.warn("MethodArgumentNotValidException 발생", exception);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(Objects.requireNonNull(exception.getFieldError()).getDefaultMessage());
    }

    @ExceptionHandler(value = DataIntegrityViolationException.class)
    private ResponseEntity<String> handleException(DataIntegrityViolationException exception) {
        log.warn("DataIntegrityViolationException 발생", exception);
        return DUPLICATE_ID.getResponse();
    }
}
