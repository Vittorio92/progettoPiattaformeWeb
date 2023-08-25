package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    boolean existsByEmail(String email);

    Utente findByEmail(String email);

    Utente findById(int id);

    void deleteById(int id);
}
