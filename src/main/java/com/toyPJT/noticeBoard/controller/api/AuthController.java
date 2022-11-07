package com.toyPJT.noticeBoard.controller.api;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.PrivateKey;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
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

    private static final String RSA_WEB_KEY = "_RSA_WEB_Key_"; // 개인키 session key
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<Void> login(@Validated @RequestBody LoginRequest loginRequest, HttpSession session) throws
        Exception {
        log.debug("POST /login");
        String userName = userService.login(loginRequest, (PrivateKey) session.getAttribute(AuthController.RSA_WEB_KEY));
        session.setAttribute(SESSION_NAME, userName);
        session.removeAttribute(AuthController.RSA_WEB_KEY);
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
