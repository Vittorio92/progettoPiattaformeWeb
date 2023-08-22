package org.example.Entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.*;

import jdk.jfr.DataAmount;
import lombok.Data;
import lombok.ToString;

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
}
