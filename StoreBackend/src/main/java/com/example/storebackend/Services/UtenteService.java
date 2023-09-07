package com.example.storebackend.Services;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Entities.Ordine;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.IndirizzoRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.OrdineInesistenteException;
import com.example.storebackend.Support.Exceptions.UtenteEsistenteException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
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

    @Autowired
    private IndirizzoRepository indirizzoRepository;
    @Autowired
    private ProdottoInCarrelloService prodottoInCarrelloService;

    @Autowired
    private OrdineService ordineService;

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
    public Utente getUtente(String email) throws UtenteInesistenteException{
        if(!utenteRepository.existsByEmail(email))
            throw new UtenteInesistenteException();
        return utenteRepository.findByEmail(email);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Utente> ricercaAvanzata(String nome, String cognome, String dataNascita, String email){
        return utenteRepository.ricercaUtente(nome, cognome, dataNascita, email);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaUtente(int id) throws UtenteInesistenteException, OrdineInesistenteException {
        if (!utenteRepository.existsById(id)) {
            throw new UtenteInesistenteException();
        }

        Utente u= utenteRepository.findById(id);

        //elimino i prodotti contenuti nel carrello dell'utente
        prodottoInCarrelloService.eliminaCarrelloUtente(u);

        //elimino gli ordini effettuati dall'utente
        List<Ordine> ordini = u.getStorico();
        for(Ordine o : ordini){
            ordineService.eliminaOrdine(o.getId());
        }

        //elimino gli indirizzi dell'utente
        List<Indirizzo> indirizzi = indirizzoRepository.findAllByUtente(u);
        for(Indirizzo i: indirizzi){
            indirizzoRepository.delete(i);
        }

        //elimino l'utente
        utenteRepository.deleteById(id);
    }
}


