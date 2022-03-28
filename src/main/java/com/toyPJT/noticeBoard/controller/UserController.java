package com.toyPJT.noticeBoard.controller;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.toyPJT.noticeBoard.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class UserController {
    private final UserService userService;

    @GetMapping("/user/updateForm")
    public String userUpdateForm(Model model, HttpSession session) {
        log.debug("GET /updateForm");
        String userName = (String)session.getAttribute(SESSION_NAME);
        model.addAttribute("user", userService.findUser(userName));
        return "user/updateForm";
    }

}
