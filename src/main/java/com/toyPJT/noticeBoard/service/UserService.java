package com.toyPJT.noticeBoard.service;

import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.domain.user.UserRepository;
import com.toyPJT.noticeBoard.dto.LoginRequest;
import com.toyPJT.noticeBoard.dto.UserSaveRequest;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Transactional
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.toEntity();
        userRepository.save(user);
    }

    @Transactional
    public void update(Integer id, UserSaveRequest userSaveRequest) {
        User user = userRepository.findById(id)
            .orElseThrow(() -> new GlobalException(USER_ID_DOES_NOT_EXIST));
        user.changeValue(userSaveRequest.toEntity());
    }

    @Transactional(readOnly = true)
    public String login(LoginRequest loginRequest) {
        User user = findUser(loginRequest.getUsername());
        if (!user.isYourPassword(loginRequest.getPassword())) {
            throw new GlobalException(PASSWORDS_DO_NOT_MATCH);
        }
        return user.getUsername();
    }

    @Transactional(readOnly = true)
    public User findUser(String userName) {
        return userRepository.findByUsername(userName)
            .orElseThrow(() -> new GlobalException(USER_NAME_DOES_NOT_EXIST));
    }
}
