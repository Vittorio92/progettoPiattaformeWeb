package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Enum.Tipologia;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProdottoRepository extends JpaRepository<Prodotto, Integer> {
    @Override
    boolean existsById(Integer id);

    Prodotto findById(int id);

    @Override
    void deleteById(Integer id);

    Boolean existsByNomeAndSquadraAndTipologia(String nome, String squadra, Tipologia tipologia);
    Prodotto findByNomeAndSquadraAndTipologia(String nome, String squadra, Tipologia tipologia);

    List<Prodotto> findBySquadra(String squadra);

    boolean existsByNome(String nome);

    List<Prodotto> findByNomeContaining(String nome);

    List<Prodotto> findByTipologia(Tipologia tipologia);


    @Query(" SELECT p "+
            "FROM Prodotto p "+
            "WHERE  (p.nome like %:nome% OR :nome IS NULL) AND "+
            "      (p.squadra LIKE %:squadra% OR :squadra IS NULL) AND "+
            "      (p.tipologia = :tipologia OR :tipologia IS NULL)"
    )
    List<Prodotto> ricercaAvanzata(@Param( value = "nome") String nome, @Param(value = "squadra") String squadra, @Param(value = "tipologia")Tipologia tipologia);
}
