package com.example.java.controller;

import com.example.java.dto.CelebrityDto;
import com.example.java.dto.EditArtworkDto;
import com.example.java.dto.UploadArtworkDto;
import com.example.java.mapper.AddArtworkMapper;
import com.example.java.mapper.EditArtworkMapper;
import com.example.java.mapper.GetArtworkMapper;
import com.example.java.mapper.ShowArtworkMapper;
import com.example.java.myExcetion.AddArtworkException;
import com.example.java.myExcetion.EditArtworkException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@CrossOrigin("http://localhost:5173")
@RestController
public class EditArtworkController {
    @Autowired
    private EditArtworkMapper editArtworkMapper;

    @Autowired
    private GetArtworkMapper getArtworkMapper;

    @Autowired
    private ShowArtworkMapper showArtworkMapper;

    @Autowired
    private AddArtworkMapper addArtworkMapper;

    private EditArtworkDto findArtworkByAid(Integer aid){
        System.out.println("开始搜索作品");
        EditArtworkDto artwork = editArtworkMapper.findArtworkByAid(aid);
        artwork.time=artwork.date.getTime()/1000;
        artwork.ip=getArtworkMapper.findIpNameByIpid(artwork.ipId);
        List<Integer> cids=editArtworkMapper.findCidByAid(aid);

        for(Integer cid:cids){
            artwork.celebritys.add(showArtworkMapper.findMainMessageByCid(cid));
        }

        // 通过cid和aid找这个作品的职工，但是存在一个人多个职位的
        Set<Integer> handleCid=new TreeSet<>();

        for(int i=0;i<artwork.celebritys.size();i++){
            if(!handleCid.contains(artwork.celebritys.get(i).getCid())){
                List<Integer> tids = editArtworkMapper.findTidByCidAndAid(aid, artwork.celebritys.get(i).getCid());
                handleCid.add(artwork.celebritys.get(i).getCid());
                int tidIndex=tids.size();
                artwork.celebritys.get(i).title=tids.get(0);
                if(tidIndex>1){
                    for(int j=artwork.celebritys.size()-1;j>i;j--){
                        if(artwork.celebritys.get(j).name.equals(artwork.celebritys.get(i).name)){
                            artwork.celebritys.get(j).title=tids.get(--tidIndex);
                        }
                    }
                }
            }
        }
        return artwork;
    }

    @GetMapping("/artwork/edit/{aid}")
    public EditArtworkDto returnArtwork(@PathVariable Integer aid){
        return findArtworkByAid(aid);
    }

    @Transactional
    @PreAuthorize("@MA.hasAuthority('管理员','超级管理员')")
    @PutMapping("/artwork")
    public void editArtwork(@Validated @RequestBody EditArtworkDto data,
                            BindingResult check){
        System.out.println("editArtwork开始运行");
        System.out.println(data);
        if(check.hasErrors()){
            throw new AddArtworkException(UploadArtworkDto.INPUT_ERROR,check);
        }
        Integer aid=data.aid;
        // 找到原来的作品详情
        EditArtworkDto artwork=findArtworkByAid(aid);

        // ip是否改变，改了的话看看是否为新的ip
        if(!data.ip.equals(artwork.ip)){
            if(addArtworkMapper.findIdByIp(data.ip).isEmpty()) {
                if(addArtworkMapper.addIp(data.ip)<=0){
                    System.out.println("添加新ip失败");
                    throw new AddArtworkException(UploadArtworkDto.ADD_IP_ERROR);
                }
            }
            else {
                Integer ipId=editArtworkMapper.findIpIdByIpName(data.ip);
                if(editArtworkMapper.updateArtworkIp(ipId,aid)<=0){
                    System.out.println("更新ip失败");
                    throw new EditArtworkException(EditArtworkDto.UPDATE_IP_ERROR);
                }
                else {
                    System.out.println("更新ip成功");
                }
            }
        }

        // 看看时间是否更新
        if(!data.time.equals(artwork.time)){
            if(editArtworkMapper.updateArtworkTime(new Date(data.time*1000),aid)<=0){
                System.out.println("更新时间失败");
                throw new EditArtworkException(EditArtworkDto.UPDATE_TIME_ERROR);
            }
            else {
                System.out.println("更新时间成功");
            }
        }

        // 看看资源地址是否更新
        if(!data.resourceAddress.equals(artwork.resourceAddress)){
            if(editArtworkMapper.updateArtworkResourceAddress(data.resourceAddress,aid)<=0){
                System.out.println("修改资源地址失败");
                throw new EditArtworkException(EditArtworkDto.UPDATE_ADDRESS_ERROR);
            }
            else {
                System.out.println("更新资源地址成功");
            }
        }

        // 看看作品图片是否更新
        if(!data.avatar.equals(artwork.avatar)){
            if(editArtworkMapper.updateArtworkAvatar(data.avatar,aid)<=0){
                System.out.println("修改作品图片地址失败");
                throw new EditArtworkException(EditArtworkDto.UPDATE_AVATAR_ERROR);
            }
            else {
                System.out.println("更新作品图片成功");
            }
        }

        // 看看作品简介是否更新
        if(!data.intro.equals(artwork.intro)){
            if(editArtworkMapper.updateArtworkIntro(data.intro,aid)<=0){
                System.out.println("修改作品简介失败");
                throw new EditArtworkException(EditArtworkDto.UPDATE_INTRO_ERROR);
            }
            else {
                System.out.println("更新作品简介成功");
            }
        }

        //看看作品名字是否更新
        if(!data.title.equals(artwork.title)){
            if(editArtworkMapper.updateArtworkName(data.title,aid)<=0){
                System.out.println("修改作品名字失败");
                throw new EditArtworkException(EditArtworkDto.UPDATE_NAME_ERROR);
            }
            else {
                System.out.println("更新作品名字成功");
            }
        }

        // 取作品的新老职工集合
        HashSet<CelebrityDto> newCelebritySet=new HashSet<>(data.celebritys);
        HashSet<CelebrityDto> oldCelebritySet=new HashSet<>(artwork.celebritys);

        Set<CelebrityDto> newAddCelebrities = newCelebritySet.stream()
                .filter(celebrityDto -> !oldCelebritySet.contains(celebrityDto))
                .collect(Collectors.toSet());
        // 浅拷贝一个集合
//        HashSet<CelebrityDto> cloneSet=new HashSet<>();
//        cloneSet=(HashSet<CelebrityDto>) newCelebritySet.clone();
//        cloneSet.removeAll(oldCelebritySet);

        // 加上职工和作品的关系
        for(var newAddCelebrity:newAddCelebrities){
            System.out.println("新加的职工："+newAddCelebrities);

            if (addArtworkMapper.addCelebrity(newAddCelebrity) <= 0) {
                throw new AddArtworkException(UploadArtworkDto.ADD_CELERITY_ERROR);
            }
            if (addArtworkMapper.addCelebrityAndArtworkRelation(
                    aid, newAddCelebrity.getCid(), newAddCelebrity.title) <= 0) {
                throw new AddArtworkException(UploadArtworkDto.ADD_AC_RELATION_ERROR);
            }
        }

        // 删除作品和职工的关系，但不删除职工
        oldCelebritySet.removeAll(newCelebritySet);
        for(var newAddCelebrity:oldCelebritySet){
            System.out.println("移去的老职工"+oldCelebritySet);

            if(editArtworkMapper.removeArtworkWithCelebrity(aid,newAddCelebrity.getCid())<=0) {
                System.out.println("删除作品和职工关系失败");
                throw new EditArtworkException(EditArtworkDto.DELETE_ArtworkWithCelebrity_ERROR);
            }
            else {
                System.out.println("删除作品和职工关系成功");
            }
        }
    }
}
