package com.toyPJT.noticeBoard.service;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.domain.user.UserRepository;
import com.toyPJT.noticeBoard.dto.UserSaveRequest;

import lombok.AllArgsConstructor;


@AllArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public void register(UserSaveRequest userSaveRequest) {
        User user = userSaveRequest.toEntity();
        userRepository.save(user);
    }
}
