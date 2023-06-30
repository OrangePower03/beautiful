package com.example.java.entity;

import jakarta.persistence.*;

@Entity
public class ArtworkUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer auId;

    @ManyToOne
    public Artwork aid;

    @ManyToOne
    public _User uid;
}
