package com.example.java.controller;

import com.example.java.mapper.DeleteArtworkMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@CrossOrigin("http://localhost:5173")
@RestController
public class DeleteArtworkController {
    @Autowired
    private DeleteArtworkMapper deleteArtworkMapper;

    @DeleteMapping("/artwork/{id}")
    @PreAuthorize("@MA.hasAuthority('管理员','超级管理员')")
    @Transactional
    public void deleteArtwork(@PathVariable Integer id){
        System.out.println("deleteArtwork开始运行");
        deleteArtworkMapper.removeArtworkWithUserByAid(id);
        //System.out.println("删除作品和用户关系成功");

        deleteArtworkMapper.removeArtworkWithCelebrityByAid(id);
        //System.out.println("删除作品和职工关系成功");

        deleteArtworkMapper.removeArtworkByAid(id);
        //System.out.println("删除作品成功");
    }
}
