package com.example.java.entity;

import jakarta.persistence.*;
import java.sql.Date;

@Entity
public class Artwork {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer aid;

    public String avatar; // 作品图片

    @Column(columnDefinition = "Text")
    public String intro; // 作品介绍

    public String name; // 作品名字
    public String resource_address; // 作品地址
    public Date time; // 作品上映时间

    @ManyToOne
    public Ip ip;

    @ManyToOne
    public Kind kind;
}
