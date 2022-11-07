package com.toyPJT.noticeBoard.service;

import static com.toyPJT.noticeBoard.exception.ExceptionType.*;

import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

import javax.crypto.Cipher;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.toyPJT.noticeBoard.controller.api.AuthController;
import com.toyPJT.noticeBoard.domain.user.User;
import com.toyPJT.noticeBoard.domain.user.UserRepository;
import com.toyPJT.noticeBoard.dto.LoginRequest;
import com.toyPJT.noticeBoard.dto.UserSaveRequest;
import com.toyPJT.noticeBoard.exception.GlobalException;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@Transactional
@Service
@Slf4j
public class UserService {

    private static final String RSA_INSTANCE = "RSA"; // rsa transformation
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
    public String login(LoginRequest loginRequest, PrivateKey privateKey) throws Exception {

        String encryptedUsername = loginRequest.getUsername();
        String encryptedPassword = loginRequest.getPassword();

        log.info("before username is {}", encryptedUsername);
        log.info("before password is {}", encryptedPassword);

        String username = decryptRsa(privateKey, encryptedUsername);
        String password = decryptRsa(privateKey, encryptedPassword);

        log.info("after decryptRsa, user name is {}", username);
        log.info("after decryptRsa, password is {}", password);

        User user = findUser(username);
        if (!user.isYourPassword(password)) {
            throw new GlobalException(PASSWORDS_DO_NOT_MATCH);
        }
        return username;
    }

    @Transactional(readOnly = true)
    public User findUser(String userName) {
        return userRepository.findByUsername(userName)
            .orElseThrow(() -> new GlobalException(USER_NAME_DOES_NOT_EXIST));
    }

    /**
     * 복호화
     *
     * @param privateKey
     * @param securedValue
     * @return
     * @throws Exception
     */
    private String decryptRsa(PrivateKey privateKey, String securedValue) throws Exception {
        Cipher cipher = Cipher.getInstance(UserService.RSA_INSTANCE);
        byte[] encryptedBytes = hexToByteArray(securedValue);
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes, StandardCharsets.UTF_8);
    }

    /**
     * 16진 문자열을 byte 배열로 변환한다.
     *
     * @param hex
     * @return
     */
    public static byte[] hexToByteArray(String hex) {
        if (hex == null || hex.length() % 2 != 0) { return new byte[] {}; }

        byte[] bytes = new byte[hex.length() / 2];
        for (int i = 0; i < hex.length(); i += 2) {
            byte value = (byte) Integer.parseInt(hex.substring(i, i + 2), 16);
            bytes[(int) Math.floor(i / 2)] = value;
        }
        return bytes;
    }
}
