package com.example.java.exceptionController;

import com.example.java.controller.EditArtworkController;
import com.example.java.myExcetion.AddArtworkException;
import com.example.java.myExcetion.EditArtworkException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;

@RestControllerAdvice(basePackageClasses = {EditArtworkController.class})
public class HandleEditArtworkException {
    @Value("${our.email}")
    private String email;

    @ExceptionHandler({EditArtworkException.class})
    public ProblemDetail handleArtworkException(AddArtworkException e){
        ProblemDetail detail=ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        String message=e.getMessage();
        detail.setTitle(message);
        detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        detail.setInstance(URI.create("/edit/artwork"));
        return detail;
    }
}
