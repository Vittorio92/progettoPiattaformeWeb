package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.ProdottiInCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProdottiInCarelloRepository extends JpaRepository<ProdottiInCarrello, Integer> {
    @Override
    boolean existsById(Integer id);
    @Override
    Optional<ProdottiInCarrello> findById(Integer id);
    @Override
    void deleteById(Integer id);

}
