package com.example.java.controller;

import com.example.java.dto.CelebrityDto;
import com.example.java.dto.GetArtworkDto;
import com.example.java.dto.UploadArtworkDto;
import com.example.java.mapper.AddArtworkMapper;
import com.example.java.mapper.GetArtworkMapper;
import com.example.java.mapper.UserMapper;
import com.example.java.myExcetion.AddArtworkException;
import com.example.java.myExcetion.ErrorRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@CrossOrigin("http://localhost:5173")
@RestController
public class GetUploadArtworkController {
    @Autowired
    private GetArtworkMapper getArtworkMapper;

    @Autowired
    private AddArtworkMapper addArtworkMapper;

    @Autowired
    private UserMapper userMapper;

    //等待前端
    @GetMapping("/artwork/user")
    public GetArtworkDto[] searchMyArtwork(@RequestParam("username") String name){
        System.out.println("searchMyArtwork开始运行");
        List<GetArtworkDto> artworkList = findByUser(name,true);
        GetArtworkDto[] artworks=new GetArtworkDto[artworkList.size()];
        System.out.println(artworkList.size());
        return artworkList.toArray(artworks);
    }

    @GetMapping("/artwork")
    public GetArtworkDto[] searchArtworks(@RequestParam("name") String searchName,
                           @RequestParam("category") String searchType){
        System.out.println("searchArtworks开始运行");
        if(searchName.isEmpty() || searchName.equals("_")){
            throw new ErrorRequest("用户妄想穷尽数据库");
        }
        Set<GetArtworkDto> artworkSet=new HashSet<>(0);
        List<GetArtworkDto> artworkList=new ArrayList<>(0);
        String search=String.format("%%%s%%",searchName);
        switch (searchType) {
            case GetArtworkDto.ALL -> {
//                System.out.println("这波是在全局搜索，搜索的内容是：" + search);
                artworkList.addAll(findByStaff(search));
                List<Integer> aids = getArtworkMapper.findAidLikeArtworkName(search);
                artworkSet.addAll(findByAids(aids));
                artworkSet.addAll(findByIp(search));
                artworkSet.addAll(findByUser(search,false));
                artworkList=artworkSet.stream().toList();
            }
            case GetArtworkDto.STAFF -> {
//                System.out.println("这波是按职工名字搜索，职工名是：" + searchName);
                artworkList = findByStaff(search);
            }
            case GetArtworkDto.IP -> {
//                System.out.println("通过ip来找作品，ip的名字是：" + searchName);
                artworkList = findByIp(search);
            }
            case GetArtworkDto.USER -> {
//                System.out.println("通过用户找作品，用户名是：" + searchName);
                artworkList = findByUser(search,false);
            }
            default -> {  // 类型查找
//                System.out.println("这波是在按类型查找，类型是：" + searchType);
                artworkList = findByType(search, searchType);
            }
        }

        GetArtworkDto[] temp=new GetArtworkDto[artworkList.size()];
        return artworkList.toArray(temp);
    }

    @Transactional
    @PostMapping("/artwork")
    public void addArtwork(@Validated @RequestBody
               UploadArtworkDto upload, BindingResult check){
        System.out.println("addArtwork开始运行");
        upload.time=new Date(upload.date*1000);
        // 判定是否用户填定表单错误
        if(check.hasErrors()){
            throw new AddArtworkException(UploadArtworkDto.INPUT_ERROR,check);
        }
        
        System.out.println("现在在添加ip");
        // 判定用户提交的ip是否存在，不存在就add，存下postIp以后给添加作品准备
        List<Integer> postIp= addArtworkMapper.findIdByIp(upload.ip);
        if (postIp.isEmpty()) {
            if(addArtworkMapper.addIp(upload.ip)<=0){
                throw new AddArtworkException(UploadArtworkDto.ADD_IP_ERROR);
            }
        }
        upload.ipId= addArtworkMapper.findIdByIp(upload.ip).get(0);

        System.out.println("添加作品");
        // 添加作品，并获取作品的id，给添加作品和职工的关系准备
        if(addArtworkMapper.addArtwork(upload)<=0){
            throw new AddArtworkException(UploadArtworkDto.ADD_ARTWORK_ERROR);
        }

        Integer artworkId=upload.getAid();
        Integer userId = userMapper.findUidByName(upload.userName).get(0);

        if(addArtworkMapper.addArtworkAndUserRelation(artworkId, userId)<=0){
            throw new AddArtworkException(UploadArtworkDto.ADD_AU_RELATION_ERROR);
        }

        System.out.println("添加职工");
        // 添加新职工，并添加职工与作品的关系
        List<CelebrityDto> celebrities = upload.celebrities;
        for (int i=0;i<celebrities.size();i++) {
            CelebrityDto celebrity=celebrities.get(i);
            List<Integer> celebrityId = addArtworkMapper.findCidByCelebrityName(celebrity.name);
            if (celebrityId.isEmpty()) {
                if (addArtworkMapper.addCelebrity(celebrity) <= 0) {
                    throw new AddArtworkException(UploadArtworkDto.ADD_CELERITY_ERROR);
                }
            }
            if (addArtworkMapper.addCelebrityAndArtworkRelation(
                    artworkId, celebrity.getCid(), celebrity.title) <= 0) {
                throw new AddArtworkException(UploadArtworkDto.ADD_AC_RELATION_ERROR);
            }
        }
    }

    // 通过aid们找所有的作品
    private List<GetArtworkDto> findByAids(List<Integer> aids){
        List<GetArtworkDto> artworks=new ArrayList<>(0);
        for(Integer aid:aids){
            artworks.addAll(getArtworkMapper.findArtworkByAid(aid));
        }
        for(GetArtworkDto artwork:artworks){
            artwork.userName=getArtworkMapper.findUserNameByAid(artwork.id);
            artwork.ip=getArtworkMapper.findIpNameByIpid(artwork.ipId);
            artwork.title=getArtworkMapper.findTitleNameByAid(artwork.id);
            artwork.kind=getArtworkMapper.findKindNameByKid(artwork.kid);
        }
        return artworks;
    }

    // 通过搜索职工找所有的作品
    private List<GetArtworkDto> findByStaff(String search){
        List<GetArtworkDto> artworks=new ArrayList<>(0);
        List<Integer> celebritiesId = getArtworkMapper.findAllCidLikeSearch(search);
        List<Integer> aids=new ArrayList<>();
        for(Integer cid:celebritiesId){
            aids.addAll(getArtworkMapper.findAidByCid(cid));
        }
        artworks=findByAids(aids);
        return artworks;
    }

    // 通过搜索类型的，找这个类型的所有作品
    private List<GetArtworkDto> findByType(String search,String searchType){
        List<GetArtworkDto> artworks=new ArrayList<>(0);
        Integer kid = getArtworkMapper.findKidBySearchType(searchType);
        List<Integer> allArtworkId = getArtworkMapper.findAllArtworkNameLikeSearch(search);
        for(Integer aid:allArtworkId){
            artworks.addAll(getArtworkMapper.findArtworkByA_Kid(aid,kid)) ;
        }

        for(GetArtworkDto artwork:artworks){
            artwork.userName=getArtworkMapper.findUserNameByAid(artwork.id);
            artwork.ip=getArtworkMapper.findIpNameByIpid(artwork.ipId);
            artwork.title=getArtworkMapper.findTitleNameByAid(artwork.id);
            artwork.kind=searchType;
        }
        return artworks;
    }
    private List<GetArtworkDto> findByIp(String search){
        List<GetArtworkDto> artworks=new ArrayList<>(0);
        List<Integer> ipIds = getArtworkMapper.findIpIdLikeIpName(search);
        for(Integer ipId: ipIds){
            artworks.addAll(getArtworkMapper.findAllArtworkByIpId(ipId));
        }
        for(GetArtworkDto artwork:artworks){
            artwork.kind=getArtworkMapper.findKindNameByKid(artwork.kid);
            artwork.userName=getArtworkMapper.findUserNameByAid(artwork.id);
            artwork.ip=getArtworkMapper.findIpNameByIpid(artwork.ipId);
            artwork.title=getArtworkMapper.findTitleNameByAid(artwork.id);
        }
        return artworks;
    }

    // 通过用户找作品
    private List<GetArtworkDto> findByUser(String search,boolean findOne){
        List<GetArtworkDto> artworks=new ArrayList<>(0);
        List<Integer> uIds;
        if(findOne){
            uIds=getArtworkMapper.findUidByUserName(search);
        }
        else{
            uIds=getArtworkMapper.findUidLikeUserName(search);
        }

        List<Integer> aids=new ArrayList<>(0);
        for(Integer uid:uIds){
            aids.addAll(getArtworkMapper.findAidByUid(uid));
        }
        artworks=findByAids(aids);
        return artworks;
    }
}
