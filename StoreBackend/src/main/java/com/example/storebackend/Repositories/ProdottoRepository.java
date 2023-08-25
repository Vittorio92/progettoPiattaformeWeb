package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Prodotto;
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
    Prodotto FindByNomeAndSquadraAndTipologia(String nome,String squadra,String tipologia);

    List<Prodotto> findBySquadra(String squadra);

}
