package com.toyPJT.noticeBoard.controller;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.Cipher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class FormController {

    private static final String RSA_WEB_KEY = "_RSA_WEB_Key_"; // 개인키 session key
    private static final String RSA_INSTANCE = "RSA"; // rsa transformation

    @GetMapping("/joinForm")
    public String joinForm() {
        log.debug("GET /joinForm");
        return "user/joinForm";
    }

    @GetMapping("/loginForm")
    public String loginForm(HttpServletRequest request, Model model) {
        log.debug("GET /loginForm");
        initRsa(request, model);
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

    /**
     * rsa 공개키, 개인키 생성
     *
     * @param request
     */
    public void initRsa(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();

        KeyPairGenerator generator;
        try {
            generator = KeyPairGenerator.getInstance(FormController.RSA_INSTANCE);
            generator.initialize(1024);

            KeyPair keyPair = generator.genKeyPair();
            KeyFactory keyFactory = KeyFactory.getInstance(FormController.RSA_INSTANCE);
            PublicKey publicKey = keyPair.getPublic();
            PrivateKey privateKey = keyPair.getPrivate();

            session.setAttribute(FormController.RSA_WEB_KEY, privateKey); // session에 RSA 개인키를 세션에 저장

            RSAPublicKeySpec publicSpec = keyFactory.getKeySpec(publicKey, RSAPublicKeySpec.class);
            String publicKeyModulus = publicSpec.getModulus().toString(16);
            String publicKeyExponent = publicSpec.getPublicExponent().toString(16);

            log.info("RSAModulus : {}, RSAExponent : {}", publicKeyModulus, publicKeyExponent);

            model.addAttribute("RSAModulus", publicKeyModulus);
            model.addAttribute("RSAExponent", publicKeyExponent);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
