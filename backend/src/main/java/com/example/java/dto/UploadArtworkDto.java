package com.example.java.dto;

import jakarta.persistence.Column;
import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class UploadArtworkDto {
    public static final String INPUT_ERROR="填写表单有误";
    public static final String ADD_IP_ERROR="添加ip失败";
    public static final String ADD_ARTWORK_ERROR="添加作品失败";
    public static final String ADD_CELERITY_ERROR="添加职工失败";
    public static final String ADD_AC_RELATION_ERROR ="添加职工和作品关系失败";
    public static final String ADD_AU_RELATION_ERROR="添加用户和作品关系失败";

    private Integer aid;

    public Integer getAid() {
        return aid;
    }

    public String title;

    public String userName;

    @URL(message = "图像地址要是一个正确的地址")
    public String avatar;

    @URL(message = "资源地址要是一个正确的地址")
    public String resourceAddress;

    public String ip;

    public Date time;

    public String intro;

    public Integer kind;

    public List<CelebrityDto> celebrities=new ArrayList<>(0);

    // 中介
    public Integer ipId;
    public Long date;
}
