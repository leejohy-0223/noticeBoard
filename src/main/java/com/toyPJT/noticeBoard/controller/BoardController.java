package com.toyPJT.noticeBoard.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.toyPJT.noticeBoard.service.BoardService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@Controller
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("boards", boardService.getBoards());
        return "index";
    }

}
