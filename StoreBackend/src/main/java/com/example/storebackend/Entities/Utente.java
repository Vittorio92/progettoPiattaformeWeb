package com.example.storebackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="Utente",schema="progettostoredb")
public class Utente {
    public Utente(String nome, String cognome, String mail){
        this.nome=nome;
        this.cognome=cognome;
        this.email=mail;
    }

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

    public Utente(){

    }
}
