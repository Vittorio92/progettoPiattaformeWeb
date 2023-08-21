package org.example.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;
import lombok.Data;

import java.util.List;
@Entity
@Data
@Table(name="utente",schema="storemagliedb")
public class Utente{
    public Utente(String mail, String nome, String cognome){
        this.mail = mail;
        this.nome = nome;
        this.cognome = cognome;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_utente", nullable = false)
    private int idUtente;

    @Basic
    @Column(name = "codice_fiscale", length = 15)
    private String codiceFiscale;

    @Basic
    @Column(name = "nome", nullable = false, length = 50)
    private String nome;

    @Basic
    @Column(name = "cognome", nullable = false, length = 50)
    private String cognome;

    @Basic
    @Column(name = "data_di_nascita", length = 10)
    private String dataDiNascita;

    @Basic
    @Column(name = "indirizzo", length = 50)
    private String indirizzo;

    @Basic
    @Column(name = "mail", length = 50)
    private String mail;


    @OneToMany()//mappedBy = "utente", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<MagliaNelCarrello> carrello;

    @OneToMany(mappedBy = "acquirente", cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JsonIgnore
    private List<Ordine> storico;


    public Utente() {

    }

}