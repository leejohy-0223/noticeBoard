package com.toyPJT.noticeBoard.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.toyPJT.noticeBoard.controller.interceptor.AccessDenyInterceptor;
import com.toyPJT.noticeBoard.controller.interceptor.AuthenticationInterceptor;

@Configuration
public class AuthConfig implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 세션 없을 시 작성된 루트에 대해서만 허용
        registry.addInterceptor(new AuthenticationInterceptor())
            .excludePathPatterns("/", "/login", "/joinForm", "/loginForm", "/image/**", "/js/**", "/user")
            .order(1);

        // 세션 있으면 아래 루트 비허용
        registry.addInterceptor(new AccessDenyInterceptor())
            .addPathPatterns("/joinForm", "/loginForm")
            .order(2);
    }
}
