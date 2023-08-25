package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Enum.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    @Override
    boolean existsById(Integer id);
    @Override
    Optional<Prodotto> findById(Integer id);

    @Override
    void deleteById(Integer id);

    Boolean existsByNomeAndSquadraAndTipologia(String nome, String squadra, Tipologia tipologia);
    Prodotto findByNomeAndSquadraAndTipologia(String nome, String squadra, Tipologia tipologia);

    List<Prodotto> findBySquadra(String squadra);

}
