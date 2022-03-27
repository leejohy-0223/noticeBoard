package com.toyPJT.noticeBoard.controller.interceptor;

import static com.toyPJT.noticeBoard.controller.SharedInformation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthenticationInterceptor implements HandlerInterceptor {

    // 세션 내용이 없으면 걸려야 함
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute(SESSION_NAME) == null) {
            log.debug("미인증 사용자 요청입니다.");
            response.sendRedirect("/loginForm");
            return false;
        }
        return true;
    }
}
