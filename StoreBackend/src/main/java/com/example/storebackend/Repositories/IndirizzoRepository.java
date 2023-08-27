package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IndirizzoRepository extends JpaRepository<Indirizzo, Integer> {

    List<Indirizzo> findAllByUtente(Utente u);

    boolean existsByCittaAndViaAndNumeroCivico(String citta, String via, int numeroCivico);

    boolean existsByCapAndViaAndNumeroCivico(int cap, String via, int numeroCivico);

    Indirizzo findByCittaAndViaAndNumeroCivico(String citta, String via, int numeroCivico);

    Indirizzo findByCapAndViaAndNumeroCivico(int cap, String via, int numeroCivico);

    void deleteById(int id);

    Indirizzo findById(int id);
}
