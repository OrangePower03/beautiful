package com.example.java.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


// springBoot的跨域请求
@Configuration
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry
                .addMapping("/**")   // 允许跨域的路径
                .allowedOriginPatterns("*")     // 允许跨域请求的域名
                .allowCredentials(true)         // 是否允许cookie
                .allowedMethods("GET","POST","DELETE","PUT")  //允许的请求方法
                .allowedHeaders("*")
                .maxAge(3600);                  // 允许跨域时间
    }
}
