package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name="Utente",schema="progettpstoredb")
public class Utente {
    public Utente(String nome, String cognome, String mail){
        this.nome=nome;
        this.cognome=cognome;
        this.mail=mail;
    }

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
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
