package com.example.java.controller;

import com.example.java.dto.CelebrityDto;
import com.example.java.dto.ShowArtworkDto;
import com.example.java.mapper.GetArtworkMapper;
import com.example.java.mapper.ShowArtworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
public class ShowArtworkController {
    @Autowired
    private ShowArtworkMapper showArtworkMapper;

    @Autowired
    private GetArtworkMapper getArtworkMapper;

    // 通过aid找到这个作品
    @GetMapping("/artwork/{aid}")
    public ShowArtworkDto findAnArtworkByAid(@PathVariable Integer aid){
        System.out.println("查找具体作品");
        List<CelebrityDto> celebrityList=new ArrayList<>(0);
        ShowArtworkDto show=showArtworkMapper.findArtworkByAid(aid);
        show.ip=getArtworkMapper.findIpNameByIpid(show.ipId);
        show.kind=getArtworkMapper.findKindNameByKid(show.kid);
        show.time=show.date.getTime()/1000;
        List<Integer> actIds=showArtworkMapper.findActIdByAid(aid);

        for(Integer actId:actIds){
            celebrityList.addAll((showArtworkMapper.findCelebrityAgencyByActId(actId)));
        }

        show.celebritys=celebrityList.toArray(new CelebrityDto[0]);
        // 添加作品和职工关系
        for(int i=0;i<show.celebritys.length;i++){
            show.celebritys[i]=showArtworkMapper.findMainMessageByCid(show.celebritys[i].getCid());
            show.celebritys[i].title=celebrityList.get(i).title;
        }

        System.out.println(show);
        return show;
    }
}
