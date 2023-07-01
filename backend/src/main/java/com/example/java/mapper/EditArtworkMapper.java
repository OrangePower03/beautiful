package com.example.java.mapper;

import com.example.java.dto.EditArtworkDto;
import org.apache.ibatis.annotations.*;

import java.util.Date;
import java.util.List;

@Mapper
public interface EditArtworkMapper {
    @Select("select aid,time,intro,kind_kid,name,avatar,resource_address,ip_ip_id from artwork where aid=#{aid}")
    @Results(value = {
        @Result(column = "aid", property = "aid"),
        @Result(column = "intro", property = "intro"),
        @Result(column = "kind_kid", property = "kind"),
        @Result(column = "time", property = "date"),
        @Result(column = "name", property = "title"),
        @Result(column = "avatar", property = "avatar"),
        @Result(column = "ip_ip_id", property = "ipId"),
        @Result(column = "resource_address", property = "resourceAddress"),
    })
    EditArtworkDto findArtworkByAid(Integer aid);

    @Select("select cid_cid from artwork_celebrity_title where aid_aid=#{aid}")
    List<Integer> findCidByAid(Integer aid);

    @Select("select tid_tid from artwork_celebrity_title where aid_aid=#{aid} and cid_cid=#{cid}")
    List<Integer> findTidByCidAndAid(Integer aid,Integer cid);

    @Select("select ip_id from ip where name=#{name}")
    Integer findIpIdByIpName(String name);

    @Update("update artwork set ip_ip_id=#{ipId} where aid=#{aid}")
    int updateArtworkIp(Integer ipId,Integer aid);

    @Update("update artwork set time=#{newTime} where aid=#{aid}")
    int updateArtworkTime(Date newTime,Integer aid);

    @Update("update artwork set resource_address=#{newAddress} where aid=#{aid}")
    int updateArtworkResourceAddress(String newAddress,Integer aid);

    @Update("update artwork set avatar=#{newAvatar} where aid=#{aid}")
    int updateArtworkAvatar(String newAvatar,Integer aid);

    @Update("update artwork set intro=#{newIntro} where aid=#{aid}")
    int updateArtworkIntro(String newIntro,Integer aid);

    @Update("update artwork set name=#{newName} where aid=#{aid}")
    int updateArtworkName(String newName,Integer aid);

    @Delete("delete from artwork_celebrity_title where aid_aid=#{aid} and cid_cid=#{cid}")
    int removeArtworkWithCelebrity(Integer aid,Integer cid);
}
