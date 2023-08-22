package com.example.storebackend.Entities;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "Indirizzo", schema = "progettostoredb")
public class Indirizzo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "citta", nullable = false)
    private String citta;

    @Column(name = "via", nullable = false)
    private String via;

    @Column(name = "numeroCivico", nullable = false)
    private int numeroCivico;

    @Column(name = "cap", nullable = false)
    private int cap;

    public Indirizzo (String citta, String via, int numeroCivico, int cap){
        this.citta=citta;
        this.via=via;
        this.numeroCivico=numeroCivico;
        this.cap=cap;
    }

    public Indirizzo(){

    }
}
