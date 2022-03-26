package com.toyPJT.noticeBoard.controller.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AccessDenyInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        log.debug("인터셉터에 걸리긴 합니다.");
        if (session != null && session.getAttribute("loginMember") != null) {
            log.debug("로그아웃을 먼저 진행합니다.");
            response.sendRedirect("/logoutCheck");
            return false;
        }
        log.debug("인터셉터에서 true가 실행됩니다.");
        return true;
    }
}
