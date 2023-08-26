package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Ordine;
import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdottoInCarrelloRepository extends JpaRepository<ProdottoInCarrello, Integer> {
    @Override
    boolean existsById(Integer id);

    ProdottoInCarrello findById(int id);

    @Override
    void deleteById(Integer id);

    ProdottoInCarrello findByUtenteAndAndProdottoAndOrdine(Utente u, Prodotto p, Ordine o);

}
