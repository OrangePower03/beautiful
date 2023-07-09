package com.example.java.controller;

import com.example.java.dto.CelebrityNameAndAvatarDto;
import com.example.java.dto.KindDto;
import com.example.java.dto.TitleDto;
import com.example.java.mapper.AddArtworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@CrossOrigin("http://localhost:5173")
@RestController
public class GetController {
    @Autowired
    private AddArtworkMapper addArtworkMapper;

    @GetMapping("/ip")
    public String[] getAllIp(){
        List<String> allIp = addArtworkMapper.findAllIp();
        if(allIp.isEmpty()){
            System.out.println("ip为空");
            return new String[0];
        }
        String[] temp=new String[allIp.size()];
        return allIp.toArray(temp);
    }

    @GetMapping("/kind")
    public KindDto[] getAllKind(){
        List<KindDto> allKind = addArtworkMapper.findAllKind();
        if(allKind.isEmpty()){
            System.out.println("kind为空");
            return new KindDto[0];
        }
        KindDto[] temp=new KindDto[allKind.size()];
        return allKind.toArray(temp);
    }

    @GetMapping("/celebrity")
    public CelebrityNameAndAvatarDto[] getAllCelebrity(){
        List<CelebrityNameAndAvatarDto> allCelebrity = addArtworkMapper.findAllCelebrityName();
        if(allCelebrity.isEmpty()){
            System.out.println("celebrity为空");
            return new CelebrityNameAndAvatarDto[0];
        }
        return allCelebrity.toArray(new CelebrityNameAndAvatarDto[0]);
    }

    @GetMapping("/title")
    public TitleDto[] getAllTitle(){
        List<TitleDto> allTitle = addArtworkMapper.findAllTitle();
        if(allTitle.isEmpty()){
            System.out.println("title为空");
            return new TitleDto[0];
        }
        TitleDto[] temp=new TitleDto[allTitle.size()];
        return allTitle.toArray(temp);
    }
}

