package com.example.java.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Kind {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer kid;

    public String name; // 种类：番剧，漫画，小说
}
