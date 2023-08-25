package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.ProdottoInCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdottoInCarrelloRepository extends JpaRepository<ProdottoInCarrello, Integer> {
    @Override
    boolean existsById(Integer id);
    @Override
    Optional<ProdottoInCarrello> findById(Integer id);
    @Override
    void deleteById(Integer id);

}
