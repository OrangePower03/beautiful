package com.example.java.dto;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Component
public class ShowArtworkDto {
    private Integer id;

    public String atitle;

    public String avatar;

    public String intro;

    public String ip;

    public String kind;

    public String resourceAddress;

    public Long time;

    public CelebrityDto[] celebritys;

    public Integer getId() {
        return id;
    }

    // 中介
    public Integer ipId;
    public Integer kid;
    public Date date;

    @Override
    public String toString() {
        CelebrityDto celebrityDto=new CelebrityDto();
        if(celebritys.length>0){
            celebrityDto=celebritys[0];
        }
        return "ShowArtworkDto{" +
                "id=" + id +
                ", atitle='" + atitle + '\'' +
                ", avatar='" + avatar + '\'' +
                ", intro='" + intro + '\'' +
                ", ip='" + ip + '\'' +
                ", kind='" + kind + '\'' +
                ", resourceAddress='" + resourceAddress + '\'' +
                ", time=" + time +
                ", celebritys其中之一=" + celebrityDto +
                ", ipId=" + ipId +
                ", kid=" + kid +
                '}';
    }
}
