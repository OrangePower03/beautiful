package com.example.java.entity;

import jakarta.persistence.*;

@Entity
public class Role {
    public static final String SIMPLE="普通用户";
    public static final String MANAGE="管理员";
    public static final String SUPER_MANAGER="超级管理员";

    @Id
    public Long rid;

    public String name;
}
