package com.example.java.config;

import com.example.java.interceptors.JwtInterceptor;
import com.example.java.interceptors.ReFlushTokenInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        registry.addInterceptor(new JwtInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/user/**");

        // 更新token的拦截器
//        registry.addInterceptor(new ReFlushTokenInterceptor())
//                .addPathPatterns("/**")
//                .excludePathPatterns("/ip","/kind","celebrity","title")
//                .excludePathPatterns("/user/**");
    }

    // springBoot的跨域请求
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
