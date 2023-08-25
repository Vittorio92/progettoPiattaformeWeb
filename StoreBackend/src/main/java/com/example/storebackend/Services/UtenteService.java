package com.example.storebackend.Services;

import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.UtenteEsistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Utente> getAllUtenti(){
        return utenteRepository.findAll();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED) //richiede una nuova transazione
    public Utente registraUtente(Utente u) throws UtenteEsistenteException {
        if (utenteRepository.existsByEmail(u.getEmail())){
            throw new UtenteEsistenteException();
        }
        return utenteRepository.save(u);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Utente getUtente(String email){
        return utenteRepository.findByEmail(email);
    }

}
