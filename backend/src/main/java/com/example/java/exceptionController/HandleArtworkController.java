package com.example.java.exceptionController;

import com.example.java.controller.EditArtworkController;
import com.example.java.controller.GetUploadArtworkController;
import com.example.java.dto.UploadArtworkDto;
import com.example.java.myExcetion.AddArtworkException;
import com.example.java.myExcetion.ErrorRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice(basePackageClasses = {
        GetUploadArtworkController.class, EditArtworkController.class})
public class HandleArtworkController {
    @Value("${our.email}")
    private String email;

    @ExceptionHandler({ErrorRequest.class})
    public ProblemDetail handleErrorRequestException(ErrorRequest e){
        ProblemDetail detail=ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        String message=e.getMessage();
        System.out.println(message);
        detail.setTitle(message);
        detail.setDetail("错误的输入，请确认你的输入");
        detail.setInstance(URI.create("/edit/artwork"));
        return detail;
    }

    @ExceptionHandler({AddArtworkException.class})
    public ProblemDetail handleArtworkException(AddArtworkException e){
        ProblemDetail detail=ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        String message=e.getMessage();
        detail.setTitle(message);
        if(message.equals(UploadArtworkDto.INPUT_ERROR)){
            List<FieldError> errors = e.getCheck().getFieldErrors();
            StringBuilder details= new StringBuilder("\n");
            for(FieldError error:errors){
                details.append(error.getDefaultMessage()).append("\n");
            }
            details.append("请您检查一下您的输入");
            detail.setDetail(details.toString());
            detail.setStatus(HttpStatus.BAD_REQUEST);
        }
        else if(message.equals(UploadArtworkDto.ADD_IP_ERROR)){
            detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        }
        else if(message.equals(UploadArtworkDto.ADD_ARTWORK_ERROR)){
            detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        }
        else if(message.equals(UploadArtworkDto.ADD_CELERITY_ERROR)){
            detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        }
        else if(message.equals(UploadArtworkDto.ADD_AC_RELATION_ERROR)){
            detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        }
        else if(message.equals(UploadArtworkDto.ADD_AU_RELATION_ERROR)){
            detail.setDetail("由于服务器的错误导致上传失败\n"+
                    "请联系我们"+email);
        }
        else {
            detail.setDetail("发生未知错误，请联系我们"+email);
        }
        detail.setInstance(URI.create("/a/artwork"));
        return detail;
    }
}
