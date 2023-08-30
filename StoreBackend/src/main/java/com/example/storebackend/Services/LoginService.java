package com.example.storebackend.Services;

import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = false, isolation= Isolation.READ_COMMITTED)
    public Utente addOrgetUtente(String email, String nome, String cognome){
        Utente utente=null;
        if(utenteRepository.existsByEmail(email))
            utente=utenteRepository.findByEmail(email);
        else{
            utente=new Utente(nome,cognome,email);
            utenteRepository.save(utente);
        }
        return utente;

    }
}
