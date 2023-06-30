package com.example.java.mapper;

import com.example.java.dto.CelebrityDto;
import com.example.java.dto.KindDto;
import com.example.java.dto.TitleDto;
import com.example.java.dto.UploadArtworkDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AddArtworkMapper {
    // 找全部的种类(kind)，通过名字找种类的id
    @Select("select kid,name from kind")
    List<KindDto> findAllKind();
    @Select("select kid from kind where name=#{name}")
    List<Integer> findKidByKindName(String name);

    // 找全部的职位名(title)，通过职位名找id
    @Select("select tid,name from title")
    List<TitleDto> findAllTitle();
    @Select("select tid from title where name=#{name}")
    List<Integer> findTidByTitleName(String name);

    // 找全部的ip，通过ip的名字找id
    @Select("select name from ip")
    List<String> findAllIp();
    @Select("select ip_id from ip where name=#{ip}")
    List<Integer> findIdByIp(String ip);

    // 找全部职工(celebrity)，通过职工的名字找id
    @Select("select name from celebrity")
    List<String> findAllCelebrityName();
    @Select("select cid from celebrity where name=#{name}")
    List<Integer> findCidByCelebrityName(String name);

    // 找作品的id，给添加职工和作品的关系(artwork_celebrity_title)用
    @Select("select aid from artwork where name=#{name}")
    List<Integer> findAidByArtworkName(String name);


    // 添加作品
    @Insert("""
            insert into artwork(avatar,intro,name,resource_address,time,ip_ip_id,kind_kid)
            value(#{upload.avatar},#{upload.intro},#{upload.title},
            #{upload.resourceAddress},#{upload.time},#{upload.ipId},#{upload.kind})""")
    @Options(useGeneratedKeys = true, keyProperty = "aid")
    int addArtwork(@Param("upload") UploadArtworkDto addMessage);

    // 添加新的ip
    @Insert("insert into ip(name) values(#{ip})")
    int addIp(String ip);

    // 添加新的职工
    @Insert("insert into celebrity(avatar, name) values(#{avatar},#{name})")
    @Options(useGeneratedKeys = true,keyProperty = "cid")
    int addCelebrity(CelebrityDto celebrityDto);

    // 添加职工和作品的关系
    @Insert("""
        insert into artwork_celebrity_title(aid_aid, cid_cid, tid_tid)
        values(#{aid},#{cid},#{tid})
        """)
    int addCelebrityAndArtworkRelation(Integer aid,Integer cid,Integer tid);

    // 添加作品和用户的关系
    @Insert("insert into artwork_user(aid_aid, uid_uid) values(#{aid},#{uid})")
    int addArtworkAndUserRelation(@Param("aid") Integer aid,@Param("uid") Integer uid);


}
