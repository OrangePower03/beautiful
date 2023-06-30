package com.example.java.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface DeleteArtworkMapper {
    @Delete("delete from artwork where aid=#{aid}")
    int removeArtworkByAid(Integer aid);

    @Delete("delete from artwork_user where aid_aid=#{aid}")
    int removeArtworkWithUserByAid(Integer aid);

    @Delete("delete from artwork_celebrity_title where aid_aid=#{aid}")
    int removeArtworkWithCelebrityByAid(Integer aid);

}
