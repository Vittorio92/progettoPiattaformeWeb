package com.example.storebackend.Services;

import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.ProdottoInCarrelloRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.ProdottoInesistenteException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ProdottoInCarrelloService {
    @Autowired
    private ProdottoInCarrelloRepository prodottoInCarrelloRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<ProdottoInCarrello> getAllProdottiInCarrello(){
        return prodottoInCarrelloRepository.findAll();
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public ProdottoInCarrello getProdottoInCarrello(int id){
        return prodottoInCarrelloRepository.findById(id);
    }

    //metodo aggiungere prodotto al carrello

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaCarrelloUtente(Utente u) throws UtenteInesistenteException {
        //verifico esistenza utente
        if(!utenteRepository.existsByEmail(u.getEmail())){
            throw new UtenteInesistenteException();
        }

        // prendo il carrello corrente dell'utente
        List<ProdottoInCarrello> carrello = getCarrello(u.getEmail());

        //elimino i prodotti nel carrello dell'utente
        for(ProdottoInCarrello prodotto : carrello){
            prodottoInCarrelloRepository.delete(prodotto);
        }
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<ProdottoInCarrello> getCarrello(String email) throws UtenteInesistenteException {
        if(! utenteRepository.existsByEmail(email))
            throw new UtenteInesistenteException();
        Utente utente = utenteRepository.findByEmail(email);
        return  utente.getCarrello();
    }

    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public void rimuoviProdottoInCarrello(ProdottoInCarrello pic){
        prodottoInCarrelloRepository.delete(pic);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void diminuisciQuantitaProdottoInCarrello(String email, ProdottoInCarrello pic) throws UtenteInesistenteException, ProdottoInesistenteException {
        if(!utenteRepository.existsByEmail(email)){
            throw new UtenteInesistenteException();
        }
        if(!prodottoInCarrelloRepository.existsById(pic.getId())){
            throw new ProdottoInesistenteException();
        }
        List<ProdottoInCarrello> carrello = getCarrello(email);
        for(ProdottoInCarrello p : carrello){
            if(p.getId() == pic.getId()){
                if(p.getQnt()==1)
                    rimuoviProdottoInCarrello(p);
                else
                    p.setQnt(p.getQnt()-1);
            }
        }
    }
}
