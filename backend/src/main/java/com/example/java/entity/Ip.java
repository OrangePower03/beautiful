package com.example.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Ip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer ipId;

    public String name; // 作品类型，分第一、二、三季就在这了
}
