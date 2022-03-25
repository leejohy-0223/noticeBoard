package com.toyPJT.noticeBoard.controller.api;

import java.net.URI;
import java.net.URISyntaxException;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toyPJT.noticeBoard.dto.LoginRequest;
import com.toyPJT.noticeBoard.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginRequest loginRequest, HttpSession session) {
        log.debug("POST /login");
        String savedId = userService.login(loginRequest);
        session.setAttribute("loginMember", savedId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/logout")
    public ResponseEntity<Object> logout(HttpSession session) throws URISyntaxException {
        log.debug("POST /logout");
        session.invalidate();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(new URI("/"));
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }
}
