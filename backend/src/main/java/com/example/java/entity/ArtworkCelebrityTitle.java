package com.example.java.entity;

import jakarta.persistence.*;

@Entity
public class ArtworkCelebrityTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer actId;

    @ManyToOne
    public Artwork aid;

    @ManyToOne
    public Celebrity cid;

    @ManyToOne
    public Title tid;

}
