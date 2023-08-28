package com.example.storebackend.Entities;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Objects;

@Entity
@Data
@Table(name = "indirizzo", schema = "progettostoredb")
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

    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    @JoinColumn(name = "utente", referencedColumnName = "id")
    private Utente utente;

    public Indirizzo (String citta, String via, int numeroCivico, int cap){
        this.citta=citta;
        this.via=via;
        this.numeroCivico=numeroCivico;
        this.cap=cap;
    }

    public Indirizzo(){

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Indirizzo indirizzo = (Indirizzo) o;
        return numeroCivico == indirizzo.numeroCivico && cap == indirizzo.cap && Objects.equals(citta, indirizzo.citta) && Objects.equals(via, indirizzo.via);
    }

}
