package com.example.storebackend.Repositories;

import com.example.storebackend.Entities.Utente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    boolean existsByEmail(String email);

    Utente findByEmail(String email);

    Utente findById(int id);

    void deleteById(int id);

    @Query("SELECT u "+
            "FROM Utente u "+
            "WHERE (u.nome LIKE: nome OR :nome IS NULL) AND"+
            "       (u.cognome LIKE: cognome OR :cognome IS NULL) AND"+
            "       (u.dataNascita LIKE: dataNascita OR :dataNascita IS NULL) AND"+
            "       (u.email LIKE: email OR :email IS NULL) "

    )
    List<Utente> ricercaUtente(@Param(value = "nome")String nome, @Param(value = "cognome")String cognome, @Param(value = "dataNascita")String dataNascita, @Param(value = "email")String email );
}
