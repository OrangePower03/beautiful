package com.example.java.filter;

import com.example.java.dto.LoginDto;
import com.example.java.myExcetion.LoginException;
import com.example.java.utils.Redis.StringRedisUtil;
import com.example.java.utils.JwtUtil;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Objects;


@Component
public class JwtTokenFilter extends OncePerRequestFilter {
    @Autowired
    private StringRedisUtil redisUtil;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // 获取token
        String token = request.getHeader("token");
        // 不携带token的，放行
        if (!StringUtils.hasText(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 解析token
        String userId;
        try {
            userId = JwtUtil.verify(token).getClaim("id");
        } catch (Exception e) {
            e.printStackTrace();
            throw new LoginException(LoginDto.TOKEN_UNAUTHORIZED);
        }
        String redisKey = "user:" + userId;
        LoginDto login = (LoginDto)redisUtil.get(redisKey);
        System.out.println("用户信息:"+login);
        if(Objects.isNull(login)){
            throw new LoginException(LoginDto.UNVERIFIED);
        }

        // 存入安全上下文
        UsernamePasswordAuthenticationToken authenticationToken=
                new UsernamePasswordAuthenticationToken(
                        login,null,login.getAuthorities());

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        System.out.println("认证完毕");
        filterChain.doFilter(request,response);
    }
}
