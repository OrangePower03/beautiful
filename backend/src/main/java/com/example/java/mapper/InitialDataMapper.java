package com.example.java.mapper;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface InitialDataMapper {
    @Insert("insert into title(name) values(#{name})")
    int addTitle(String name);

    @Insert("insert into kind(name) values(#{name})")
    int addKind(String name);

    @Select("select count(kid) from kind")
    int findKindNumber();

    @Insert("insert into role(rid,name) values(#{rid},#{roleName})")
    int addRole(int rid,String roleName);

    @Select("select count(rid) from role")
    int findCount();
}
