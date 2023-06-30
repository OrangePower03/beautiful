package com.example.java.dto;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Date;

@Component
public class EditArtworkDto {
    public static final String UPDATE_IP_ERROR="更新ip失败";
    public static final String UPDATE_TIME_ERROR="更新时间失败";
    public static final String UPDATE_ADDRESS_ERROR="更新资源地址失败";
    public static final String UPDATE_AVATAR_ERROR="更新图片地址失败";
    public static final String UPDATE_INTRO_ERROR="更新简介失败";
    public static final String UPDATE_NAME_ERROR="更新作品名字失败";
    public static final String DELETE_ArtworkWithCelebrity_ERROR="删除职工和作品关系失败";

    public Long time;

    @URL(message = "图像地址要是一个正确的地址")
    public String avatar;

    @URL(message = "资源地址要是一个正确的地址")
    public String resourceAddress;

    public Integer aid;

    public String intro;

    public Integer kind;

    public String title;

    public String ip;

    public CelebrityDto[] celebritys=new CelebrityDto[0];

    @Override
    public String toString() {
        return "EditArtworkDto{" +
                "time=" + time +
                ", avatar='" + avatar + '\'' +
                ", resourceAddress='" + resourceAddress + '\'' +
                ", aid=" + aid +
                ", intro='" + intro + '\'' +
                ", kind=" + kind +
                ", title='" + title + '\'' +
                ", ip='" + ip + '\'' +
                ", celebritys=" + Arrays.toString(celebritys) +
                ", date=" + date +
                ", ipId=" + ipId +
                '}';
    }

    // 中介
    public Date date;
    public Integer ipId;
}
