package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.ProdottiInCarrello;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProdottiInCarelloRepository extends JpaRepository<ProdottiInCarrello, Integer> {
}
