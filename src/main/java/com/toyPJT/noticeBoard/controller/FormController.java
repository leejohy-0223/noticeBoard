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

    @GetMapping("/logoutCheck")
    public String logoutCheckForm() {
        log.debug("GET /logoutCheck");
        return "user/logoutCheck";
    }

    @GetMapping("/saveForm")
    public String saveForm() {
        log.debug("GET /saveForm");
        return "board/saveForm";
    }
}
