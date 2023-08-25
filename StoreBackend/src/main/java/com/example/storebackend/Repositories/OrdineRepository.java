package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Ordine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrdineRepository extends JpaRepository<Ordine, Integer> {
    @Override
    boolean existsById(Integer id);

    @Override
    Optional<Ordine> findById(Integer integer);
    @Override
    void deleteById(Integer id);

    @Override
    List<Ordine> findAll();

    List<Ordine> findByData(Date data);

    List<Ordine> findByDataAfter(Date data);

    List<Ordine> findByDataBefore(Date data);


}
