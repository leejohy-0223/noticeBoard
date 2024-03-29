package com.toyPJT.noticeBoard.controller.api;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;
import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.reply.Reply;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.dto.BoardTemplateRequest;
import com.toyPJT.noticeBoard.dto.ReplySaveRequest;
import com.toyPJT.noticeBoard.exception.GlobalException;
import com.toyPJT.noticeBoard.service.BoardService;
import com.toyPJT.noticeBoard.service.UserService;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@AllArgsConstructor
@RestController
public class BoardApiController {

    private final BoardService boardService;
    private final UserService userService;

    @PostMapping("/board")
    public ResponseEntity<Void> save(@Validated @RequestBody BoardTemplateRequest request, HttpSession httpSession) {
        log.debug("POST /board");
        User user = getUser(httpSession);
        boardService.save(request, user);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping("/board/{id}")
    public ResponseEntity<Void> update(@PathVariable("id") Integer id, @Validated @RequestBody BoardTemplateRequest request) {
        log.debug("PUT /board/{}", id);
        boardService.update(id, request);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Integer id, HttpSession session) {
        log.debug("DELETE /board/{}", id);

        String loginMemberName = (String)session.getAttribute(SESSION_NAME);
        if (!boardService.checkBoardWriterMatches(id, loginMemberName)) {
            throw new GlobalException(NOT_A_WRITER);
        }

        boardService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/board/{boardId}/reply")
    public ResponseEntity<Void> saveReply(@PathVariable("boardId") Integer id, @Validated @RequestBody ReplySaveRequest request,
        HttpSession httpSession) {
        log.debug("POST /board/{}/reply", id);
        boardService.saveReply(request, id, getUser(httpSession));
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @DeleteMapping("/board/reply/{replyId}")
    public ResponseEntity<Void> deleteReply(@PathVariable("replyId") Integer replyId, HttpSession session) {
        log.debug("DELETE /board/reply/{}", replyId);

        String loginMemberName = (String)session.getAttribute(SESSION_NAME);
        if (!boardService.checkReplyWriterMatches(replyId, loginMemberName)) {
            throw new GlobalException(NOT_A_WRITER);
        }

        boardService.deleteReply(replyId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private User getUser(HttpSession httpSession) {
        String userName = (String)httpSession.getAttribute(SESSION_NAME);
        return userService.findUser(userName);
    }

    @GetMapping("/board/test")
    public ResponseEntity<String> test() {
        Board test = boardService.getTest();
        log.info("before call user");
        User user = test.getUser();
        log.info("after call user");

        log.info("before call replies");
        List<Reply> replies = test.getReplies();
        log.info("after call replies");

        log.info("before call replies method");
        Reply reply = replies.get(0);
        log.info("is proxy? = {}", reply.getClass());
        log.info("after call replies method");
        log.info("is proxy? = {}", reply.getClass());

        log.info("before call user method");
        log.info("proxy user class = {}", user.getClass());
        String name = user.getUsername();
        log.info("after call user method");
        log.info("proxy user class = {}", user.getClass());

        return ResponseEntity.ok(name);
    }

}
