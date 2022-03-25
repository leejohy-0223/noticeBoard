package com.toyPJT.noticeBoard.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.domain.user.UserRepository;
import com.toyPJT.noticeBoard.dto.LoginRequest;
import com.toyPJT.noticeBoard.dto.UserSaveRequest;
import com.toyPJT.noticeBoard.exception.ExceptionType;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Slf4j
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.toEntity();
        userRepository.save(user);
    }

    @Transactional(readOnly = true)
    public void login(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
            .orElseThrow(() -> new GlobalException(ExceptionType.ID_DOES_NOT_EXIST));

        if (!user.isYourPassword(loginRequest.getPassword())) {
            throw new GlobalException(ExceptionType.PASSWORDS_DO_NOT_MATCH);
        }
        log.debug("user info = {}, {}", user.getUsername(), user.getPassword());
    }
}