package com.example.storebackend.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "ordine", schema = "progettostoredb")
public class Ordine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private int id;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "data", nullable = false)
    @CreationTimestamp
    private Date data;

    @ManyToOne
    @JoinColumn(name = "utente")
    private Utente acquirente;

    @ManyToOne
    @JoinColumn(name = "indirizzo")
    private Indirizzo spedizione;

    @OneToMany(mappedBy = "ordine", cascade = CascadeType.ALL)
    private List<ProdottoInCarrello> carrello;

    public Ordine(List<ProdottoInCarrello> carrello, Utente acquirente, Indirizzo spedizione){
        this.acquirente=acquirente;
        this.carrello=carrello;
        this.spedizione=spedizione;

    }

    public Ordine(){}
}
