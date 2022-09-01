package com.toyPJT.noticeBoard.service;

import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.domain.board.Board;
import com.toyPJT.noticeBoard.domain.board.BoardRepository;
import com.toyPJT.noticeBoard.domain.reply.Reply;
import com.toyPJT.noticeBoard.domain.reply.ReplyRepository;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.dto.BoardSaveRequest;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    public void save(BoardSaveRequest boardSaveRequest, User user) {
        Board board = Board.builder()
            .user(user)
            .title(boardSaveRequest.getTitle())
            .content(boardSaveRequest.getContent())
            .count(0)
            .build();
        boardRepository.save(board);
    }

    @Transactional(readOnly = true)
    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public Board getBoard(Integer id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new GlobalException(BOARD_DOES_NOT_EXIST));
        board.increaseCount();
        return board;
    }

    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

    public void update(Integer id, Board updateBoard) {
        Board board = getBoard(id);
        board.updateValue(updateBoard);
    }

    public void saveReply(Reply reply, Integer boardId, User user) {
        Board board = getBoard(boardId);
        reply.setBoard(board);
        reply.setUser(user);
        replyRepository.save(reply);
    }

    public void deleteReply(Integer replyId) {
        replyRepository.deleteById(replyId);
    }

    public boolean checkBoardWriterMatches(Integer boardId, String loginMemberName) {
        Board board = getBoard(boardId);
        return board.getUser().isYourName(loginMemberName);
    }

    public boolean checkReplyWriterMatches(Integer replyId, String loginMemberName) {
        Reply reply = replyRepository.findById(replyId)
            .orElseThrow(() -> new GlobalException(REPLY_DOES_NOT_EXIST));
        return reply.getUser().isYourName(loginMemberName);
    }
}
