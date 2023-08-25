package com.example.storebackend.Entities;

import com.example.storebackend.Enum.Tipologia;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Data
@Table(name="prodotto", schema="progettostoredb")
public class Prodotto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "immagine", nullable = false, length = 500)
    @JsonIgnore
    private String immagine;

    @Column(name = "qnt", nullable = false)
    private int qnt;

    @Column(name = "prezzo", nullable = false)
    private float prezzo;

    @Version
    @Column(name = "versione", nullable = false)
    @JsonIgnore
    private long versione;

    @Column(name = "squadra", nullable = false)
    private String squadra;

    @Column(name = "tipologia")
    @Enumerated(EnumType.STRING)
    private Tipologia tipologia;

    @OneToMany(targetEntity = ProdottoInCarrello.class, mappedBy = "prodotto", cascade = CascadeType.MERGE)
    @JsonIgnore
    private List<ProdottoInCarrello> prodottiNelCarrello;

    public Prodotto(int qnt, float prezzo, String nome, String squadra, Tipologia tipologia, String immagine){
        this.qnt=qnt;
        this.prezzo=prezzo;
        this.nome=nome;
        this.squadra=squadra;
        this.tipologia=tipologia;
        this.immagine=immagine;
    }

    public Prodotto(){

    }
}
