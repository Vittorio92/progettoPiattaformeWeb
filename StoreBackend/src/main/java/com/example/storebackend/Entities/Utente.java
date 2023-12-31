package com.example.storebackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
@Table(name="utente",schema="progettostoredb")
public class Utente {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="id", nullable=false)
    private int id;

    @Basic
    @Column(name="nome",nullable=false)
    private String nome;

    @Basic
    @Column(name="cognome",nullable=false)
    private String cognome;

    @Basic
    @Column(name="email",nullable=false)
    private String email;

    @Column(name = "dataNascita")
    private String dataNascita;

    @OneToMany(mappedBy = "utente", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<ProdottoInCarrello> carrello;

    @OneToMany(mappedBy = "acquirente", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<Ordine> storico;

    public Utente(String nome, String cognome, String email, String dataNascita){
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
        this.dataNascita=dataNascita;
    }

    public Utente(String nome, String cognome, String email){
        this.nome=nome;
        this.cognome=cognome;
        this.email=email;
    }

    public Utente(){

    }
}
