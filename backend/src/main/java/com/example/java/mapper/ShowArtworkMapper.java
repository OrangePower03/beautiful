package com.example.java.mapper;

import com.example.java.dto.CelebrityDto;
import com.example.java.dto.ShowArtworkDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ShowArtworkMapper {
    // 通过aid找作品，但是不能找到职工
    @Select("""
        select aid,avatar,intro,name,time,resource_address,ip_ip_id,kind_kid from artwork
        where aid=#{aid}
    """)
    @Results(id = "artwork",value = {
        @Result(column = "aid", property = "id"),
        @Result(column = "avatar", property = "avatar"),
        @Result(column = "intro", property = "intro"),
        @Result(column = "name", property = "atitle"),
        @Result(column = "resource_address", property = "resourceAddress"),
        @Result(column = "ip_ip_id", property = "ipId"),
        @Result(column = "kind_kid",property = "kid"),
        @Result(column = "time",property = "date")
    })
    ShowArtworkDto findArtworkByAid(Integer aid);

    // 通过aid找到act_id
    @Select("select act_id from artwork_celebrity_title where aid_aid=#{aid}")
    List<Integer> findActIdByAid(Integer aid);

    // 找这个的中介，找到后就可以找到职工的职位和信息了
    @Select("select cid_cid, tid_tid from artwork_celebrity_title where act_id=#{actId}")
    @Results({
        @Result(column = "cid_cid",property = "cid"),
        @Result(column = "tid_tid",property = "title")
    })
    List<CelebrityDto> findCelebrityAgencyByActId(Integer actId);

    // 通过cid找职工的主要信息
    @Select("select cid,avatar, name from celebrity where cid=#{cid}")
    CelebrityDto findMainMessageByCid(Integer cid);
}
