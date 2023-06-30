package com.example.java.entity;

import jakarta.persistence.*;

@Entity
public class _User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer uid;

    public String account;
    public String password;
    public String name;
    public String imageUri;
    private Integer tag=0;

    public Integer getTag() {
        return tag;
    }
    public void setTag(Integer tag) {
        this.tag = tag;
    }

    public _User(){
        this.imageUri="default.png";
    }
}
