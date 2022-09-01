package com.toyPJT.noticeBoard.controller.api;

import static org.springframework.http.HttpStatus.*;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toyPJT.noticeBoard.dto.UserSaveRequest;
import com.toyPJT.noticeBoard.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class UserApiController {

    private final UserService userService;

    @PostMapping("/user")
    public ResponseEntity<Void> join(@Validated @RequestBody UserSaveRequest userSaveRequest) {
        log.debug("POST /user");
        userService.register(userSaveRequest);
        return new ResponseEntity<>(CREATED);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id,
        @Validated @RequestBody UserSaveRequest userSaveRequest) {
        log.debug("PUT /user/{}", id);
        userService.update(id, userSaveRequest);
        return new ResponseEntity<>(OK);
    }

}
