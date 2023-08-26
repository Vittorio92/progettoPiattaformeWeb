package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Ordine;
import com.example.storebackend.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    @Override
    boolean existsById(Integer id);

    Ordine findById(int id);
    @Override
    void deleteById(Integer id);

    @Override
    List<Ordine> findAll();

    List<Ordine> findByData(Date data);

    List<Ordine> findByDataAfter(Date data);

    List<Ordine> findByDataBefore(Date data);

    List<Ordine> findByAcquirente(Utente acquirente);

    boolean existsByAcquirente(Utente acquirente);

    @Query("SELECT o "+
            "FROM Ordine o "+
            "WHERE O.data >= :inizio AND o.data <= :fine AND o.acquirente=: acquirente"
    )
    List<Ordine> findInPeriodAcquirente(@Param(value = "inizio")Date inizio, @Param(value = "fine")Date fine, @Param(value = "acquirente")Utente acquirente);

}
