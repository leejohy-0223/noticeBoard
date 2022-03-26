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
        // registry.addInterceptor(new AuthenticationInterceptor())
        //     .excludePathPatterns("/", "/joinForm", "/loginForm", "/image/**", "/js/**")
        //     .order(1);

        registry.addInterceptor(new AccessDenyInterceptor())
            .addPathPatterns("/joinForm", "/loginForm")
            .order(1);
    }
}
