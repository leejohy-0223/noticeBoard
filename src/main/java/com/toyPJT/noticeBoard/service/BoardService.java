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
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional(readOnly = true)
@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final ReplyRepository replyRepository;

    @Transactional
    public void save(Board board, User user) {
        board.setCount(0);
        board.setUser(user);
        boardRepository.save(board);
    }

    public Page<Board> getBoards(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    @Transactional
    public Board getBoard(Integer id) {
        Board board = boardRepository.findById(id)
            .orElseThrow(() -> new GlobalException(BOARD_DOES_NOT_EXIST));
        board.increaseCount();
        return board;
    }

    @Transactional
    public void delete(Integer id) {
        boardRepository.deleteById(id);
    }

    @Transactional
    public void update(Integer id, Board updateBoard) {
        Board board = getBoard(id);
        board.updateValue(updateBoard);
    }

    @Transactional
    public void saveReply(Reply reply, Integer boardId, User user) {
        Board board = getBoard(boardId);
        reply.setBoard(board);
        reply.setUser(user);
        replyRepository.save(reply);
    }

    @Transactional
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
