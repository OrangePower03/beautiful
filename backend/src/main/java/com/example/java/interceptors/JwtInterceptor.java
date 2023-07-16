package com.example.java.interceptors;

import com.auth0.jwt.exceptions.AlgorithmMismatchException;
import com.auth0.jwt.exceptions.InvalidClaimException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.java.utils.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.net.URI;
import java.util.Objects;

public class JwtInterceptor implements HandlerInterceptor {
    private String token;
    @Override
    public boolean preHandle(HttpServletRequest request,
                 HttpServletResponse response, Object handler) throws Exception {
        /*
        * 发现cors跨域复杂请求会先发送一个方法为OPTIONS的预检请求
        * 这个请求是用来验证本次请求是否安全的
        * 第二个过滤器判断token时会把预请求当做真正的请求去判断
        * 所以在第二个拦截器判断token之前先判断是不是预请求OPTIONS
        * 不是则验证token，是则放行
        */
        if (!request.getMethod().equals("OPTIONS")) {
            response.setHeader("token", JwtUtil.build().getToken());
            ProblemDetail detail = ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
            token = request.getHeader("token");

            System.out.println("令牌：" + token);
            if (Objects.isNull(token)) {
                detail.setTitle("您持有空的令牌");
                detail.setDetail("请您重新登陆");
                return false;
            }
            try {
                JwtUtil.verify(token);
                return true;
            } catch (SignatureVerificationException e) {
                e.printStackTrace();
                detail.setTitle("签名错误异常");
            } catch (AlgorithmMismatchException e) {
                e.printStackTrace();
                detail.setTitle("算法错误异常");
            } catch (TokenExpiredException e) {
                e.printStackTrace();
                detail.setTitle("令牌过期异常");
            } catch (InvalidClaimException e) {
                e.printStackTrace();
                detail.setTitle("无效数据段");
            }
            detail.setDetail("您的令牌失效了，请重新登录");
            detail.setInstance(URI.create(request.getRequestURI()));
            String json = new ObjectMapper().writeValueAsString(detail);
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().println(json);
            System.out.println("json数据：" + json);
            return false;
        }
        else {
            return true;
        }
    }
}
