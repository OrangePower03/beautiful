package com.example.java.mapper;

import com.example.java.dto.LoginDto;
import com.example.java.dto.RegisterDto;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserMapper {
    @Select("select uid from _user where name=#{username}")
    List<Integer> findUidByName(@Param("username") String username);

    @Select("select uid from _user where account=#{account}")
    List<Integer> findByAccount(@Param("account") String account);

    @Insert("""
        insert into _user(name,account,password,tag,image_uri) values
        (#{username},#{account},#{password},0,'default.png')
        """)
    int addUser(RegisterDto newUser);

    @Select("select uid from _user where account=#{account}")
    List<Integer> findUidByAccount(@Param("account") String account);

    @Select("select name from _user where account=#{account}")
    List<String> findUserNameByAccount(@Param("account") String account);

    @Select("select tag from _user where account=#{account}")
    Integer findTagByAccount(String account);

    @Select("select uid from _user where password=#{password}")
    List<Integer> findUidByPassword(@Param("password") String password);

    @Select("select uid,account,password, name, tag from _user where account=#{account}")
    LoginDto findUserByAccount(String account);

    @Select("select uid,account,password, name, tag from _user where account=#{username}")
    LoginDto findUserByUsername(String username);
}
