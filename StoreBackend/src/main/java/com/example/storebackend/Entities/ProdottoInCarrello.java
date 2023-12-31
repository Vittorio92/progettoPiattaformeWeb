package com.example.storebackend.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "prodottiincarrello", schema = "progettostoredb")
public class ProdottoInCarrello {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "qnt", nullable = false)
    private int qnt;

    @Column(name = "prezzo", nullable = false)
    private float prezzo;


    @ManyToOne(cascade = CascadeType.MERGE, optional = false)
    @JoinColumn(name = "prodotto", referencedColumnName = "id")
    private Prodotto prodotto;


    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "utente", referencedColumnName = "id")
    @JsonIgnore
    private Utente utente;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "ordine", referencedColumnName = "id")
    @JsonIgnore
    private Ordine ordine;

    public ProdottoInCarrello(Prodotto p, int qnt, float prezzo, Utente u){
        this.prezzo=prezzo;
        this.qnt=qnt;
        this.prodotto=p;
        this.utente=u;
    }

    public ProdottoInCarrello(){

    }
}
