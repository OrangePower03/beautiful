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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

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
        EditArtworkDto artwork = editArtworkMapper.findArtworkByAid(aid);
        artwork.time=artwork.date.getTime()/1000;
        artwork.ip=getArtworkMapper.findIpNameByIpid(artwork.ipId);
        List<Integer> cids=editArtworkMapper.findCidByAid(aid);
        List<CelebrityDto> celebrityList=new ArrayList<>(0);
        for(Integer cid:cids){
            celebrityList.add(showArtworkMapper.findMainMessageByCid(cid));
        }
        for(CelebrityDto celebrity:celebrityList){
            celebrity.title=editArtworkMapper.findTidByCid(celebrity.getCid());
        }
        artwork.celebritys=new CelebrityDto[celebrityList.size()];
        celebrityList.toArray(artwork.celebritys);
        return artwork;
    }

    @GetMapping("/artwork/edit/{aid}")
    public EditArtworkDto returnArtwork(@PathVariable Integer aid){
        return findArtworkByAid(aid);
    }

    @Transactional
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
        HashSet<CelebrityDto> newCelebritySet=new HashSet<>(0);
        HashSet<CelebrityDto> oldCelebritySet=new HashSet<>(0);
        newCelebritySet.addAll(Arrays.asList(data.celebritys));
        oldCelebritySet.addAll(Arrays.asList(artwork.celebritys));

        // 浅拷贝一个集合
        HashSet<CelebrityDto> cloneSet=new HashSet<>();
        cloneSet=(HashSet<CelebrityDto>) newCelebritySet.clone();
        cloneSet.removeAll(oldCelebritySet);

        // 加上职工和作品的关系
        for(var newAddCelebrity:cloneSet){
            System.out.println("新加的职工："+cloneSet);

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
