package com.toyPJT.noticeBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormController {

    @GetMapping("/joinForm")
    public String joinForm() {
        log.debug("GET /joinForm");
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm() {
        log.debug("GET /loginForm");
        return "user/loginForm";
    }
}
