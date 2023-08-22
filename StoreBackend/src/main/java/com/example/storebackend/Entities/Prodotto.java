package com.example.storebackend.Entities;

import lombok.Data;

import jakarta.persistence.*;

@Entity
@Data
@Table(name="prodotto", schema="progettostoredb")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Basic
    @Column(name = "qnt", nullable = false)
    private int qnt;
    @Basic
    @Column(name = "prezzo", nullable = false)
    private float prezzo;

    public Prodotto(int qnt, float prezzo){
        this.qnt=qnt;
        this.prezzo=prezzo;
    }

    public Prodotto(){

    }
}
