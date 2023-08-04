package com.example.java.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface RoleMapper {
    @Select("""
            select name from role where rid in(
            select tag from _user where account=#{account})""")
    List<String> findRoleByAccount(String account);
}
