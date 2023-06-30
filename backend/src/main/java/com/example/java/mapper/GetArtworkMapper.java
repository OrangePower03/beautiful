package com.example.java.mapper;

import com.example.java.dto.GetArtworkDto;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface GetArtworkMapper {
    // 找所有名字相近的职工
    @Select("select cid from celebrity where name like #{searchType}")
    List<Integer> findAllCelebrityNameLikeSearchName(String searchType);

    // 找所有名字相近的作品aid
    @Select("select aid from artwork where name like #{search}")
    List<Integer> findAllArtworkNameLikeSearch(String search);

    // 通过搜索的类型找kid
    @Select("select kid from kind where name=#{searchType}")
    Integer findKidBySearchType(String searchType);

    // 通过作品id找上传的用户
    @Select("""
    select name from _user where uid in(
    select uid_uid from artwork_user where aid_aid=#{aid})""")
    String findUserNameByAid(Integer aid);

    // 通过aid和kid找作品的主要数据，包括aid，用来查找后面的东西
    @Select("""
    select aid,avatar,intro,name,resource_address,ip_ip_id,kind_kid from artwork
    where kind_kid=#{kid} and aid=#{aid}
    """)
    @Results(id = "artwork",value = {
        @Result(column = "aid", property = "id"),
        @Result(column = "avatar", property = "avatar"),
        @Result(column = "intro", property = "intro"),
        @Result(column = "name", property = "atitle"),
        @Result(column = "resource_address", property = "resourceAddress"),
        @Result(column = "ip_ip_id", property = "ipId"),
        @Result(column = "kind_kid",property = "kid")
    })
    List<GetArtworkDto> findArtworkByA_Kid(Integer aid, Integer kid);

    // 通过ip的id找ip的name
    @Select("select name from ip where ip_id=#{ipId}")
    String findIpNameByIpid(Integer ipId);

    // 通过aid找title的name
    @Select("""
    select name from title where tid in(
    select tid_tid from artwork_celebrity_title where aid_aid=#{aid})
    """)
    String findTitleNameByAid(Integer aid);

    // 通过职工名字找cid
    @Select("select cid from celebrity where name like #{search}")
    List<Integer> findAllCidLikeSearch(String search);

    // 通过cid找aid
    @Select("select aid_aid from artwork_celebrity_title where cid_cid=#{cid}")
    List<Integer> findAidByCid(Integer cid);

    // 通过aid找作品
    @Select("""
        select aid,avatar,intro,name,resource_address,ip_ip_id,kind_kid from artwork
        where aid=#{aid}
    """)
    @ResultMap("artwork")
    List<GetArtworkDto> findArtworkByAid(Integer aid);

    // 通过kid找kind
    @Select("select name from kind where kid=#{kid}")
    String findKindNameByKid(Integer kid);

    // 通过作品名字找aid
    @Select("select aid from artwork where name like #{search}")
    List<Integer> findAidLikeArtworkName(String search);



    // 通过ip的名字找ipId
    @Select("select ip_id from ip where name like #{search}")
    List<Integer> findIpIdLikeIpName(String search);

    //找到所有为这个ip的作品，首先要先找到ipid
    @Select("""
            select aid,avatar,intro,name,resource_address,ip_ip_id,kind_kid from artwork
            where ip_ip_id=#{ipId}
       """)
    @ResultMap("artwork")
    List<GetArtworkDto> findAllArtworkByIpId(Integer ipId);

    // 通过用户的名字找uid
    @Select("select uid from _user where name like #{searchName}")
    List<Integer> findUidLikeUserName(String searchName);

    // 准确寻找这个用户上传的作品
    @Select("select uid from _user where name=#{username}")
    List<Integer> findUidByUserName(String username);

    // 通过uid找aid
    @Select("select aid_aid from artwork_user where uid_uid=#{uid}")
    List<Integer> findAidByUid(Integer uid);
}
