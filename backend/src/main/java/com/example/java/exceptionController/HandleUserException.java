package com.example.java.exceptionController;

import com.example.java.dto.LoginDto;
import com.example.java.dto.RegisterDto;
import com.example.java.myExcetion.LoginException;
import com.example.java.myExcetion.RegisterException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class HandleUserException {
    @Value("${our.email}")
    private String email;

    @ExceptionHandler({RegisterException.class})
    public ProblemDetail handleRegisterException(RegisterException e){
        ProblemDetail detail=ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        String message=e.getMessage();
        if(message.equals(RegisterDto.ACCOUNT_REPEAT)){
            detail.setTitle(message);
            detail.setDetail("账号已被抢先创建，请您更改账号");

        }
        else if(message.equals(RegisterDto.NAME_REPEAT)){
            detail.setTitle(message);
            detail.setDetail("用户名已被抢先创建，请您再选一个更霸气的名字");
            detail.setInstance(URI.create("/register"));
        }
        else if (message.equals(RegisterDto.CHECK_NO_PASS)) {
            BindingResult check=e.getCheck();
            List<FieldError> errors = check.getFieldErrors();
            StringBuilder details= new StringBuilder("\n");
            for(FieldError error:errors){
                details.append(error.getDefaultMessage()).append("\n");
            }
            details.append("请您检查一下您的输入");
            detail.setTitle("您填写表单有误：");
            detail.setDetail(details.toString());
        }
        else if(message.equals(RegisterDto.TWICE_PASSWORD_DISABLE)){
            detail.setTitle("两次密码不一样");
            detail.setDetail("您填写的两次密码不一样，请重新再填写");
        }
        else{
            detail.setTitle("未知错误");
            detail.setDetail("发生未知错误，请联系我们，邮箱:"+this.email);
        }
        detail.setInstance(URI.create("/register"));
        // 这里呢，加点服务器的错误提醒
        System.out.println("用户登录是发生错误");
        return detail;
    }

    @ExceptionHandler({LoginException.class})
    public ProblemDetail handleLoginException(LoginException e){
        String message=e.getMessage();
        ProblemDetail detail=ProblemDetail.forStatus(HttpStatus.BAD_REQUEST);
        if(message.equals(LoginDto.ACCOUNT_EMPTY)){
            detail.setTitle(LoginDto.ACCOUNT_EMPTY);
            detail.setDetail("""
                您所填写的账号未能找到，请您仔细核对
                若您认为是一种错误，请联系我们的邮箱"""+this.email);
        }
        else if(message.equals(LoginDto.PASSWORD_ERROR)){
            detail.setTitle(LoginDto.PASSWORD_ERROR);
            detail.setDetail("您所填写的密码错误，请您仔细核对");
        }
        else {
            detail.setTitle("未知错误");
            detail.setDetail("发生未知错误，请联系我们"+this.email);
        }
        detail.setInstance(URI.create("/login"));
        return detail;
    }
}
