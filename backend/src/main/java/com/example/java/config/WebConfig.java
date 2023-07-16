package com.example.java.config;

import com.example.java.interceptors.JwtInterceptor;
import com.example.java.interceptors.ReFlushTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/login","/register");

        registry.addInterceptor(new ReFlushTokenInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/ip","/kind","celebrity","title")
                .excludePathPatterns("/login","/register");
    }
}
