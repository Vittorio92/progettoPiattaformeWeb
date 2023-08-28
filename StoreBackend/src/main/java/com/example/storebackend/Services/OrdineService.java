package com.example.storebackend.Services;

import com.example.storebackend.Entities.Ordine;
import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.OrdineRepository;
import com.example.storebackend.Repositories.ProdottoInCarrelloRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Service
public class OrdineService {

    @Autowired
    private OrdineRepository ordineRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProdottoInCarrelloRepository prodottoInCarrelloRepository;

    @PersistenceContext
    private EntityManager entityManager;
    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> getOrdiniUtente(String email) throws UtenteInesistenteException {
        if ( !utenteRepository.existsByEmail(email) ) {
            throw new UtenteInesistenteException();
        }
        return ordineRepository.findByAcquirente(utenteRepository.findByEmail(email));
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaOrdine(int id) throws OrdineInesistenteException {
        // verifico esistenza ordine
        if(!ordineRepository.existsById(id)){
            throw new OrdineInesistenteException();
        }
        Ordine o = ordineRepository.findById(id);

        // elimino i prodotti relativi al carrello dell'ordine
        if(o.getCarrello()!= null){
            for(ProdottoInCarrello prodotto : o.getCarrello()){
                prodottoInCarrelloRepository.delete(prodotto);
            }
        }
        //elimino l'ordine
        ordineRepository.deleteById(id);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Ordine aggiornaOrdine(Ordine ordine) throws OrdineInesistenteException {
        if(!ordineRepository.existsById(ordine.getId())){
            throw new OrdineInesistenteException();
        }
        return ordineRepository.save(ordine);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> ricercaData(Date data){
        return ordineRepository.findByData(data);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS)
    public List<Ordine> ricercaPerPeriodoUtente(Date inizio, Date fine, Utente utente){
        return ordineRepository.findInPeriodAcquirente(inizio, fine, utente);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Ordine> findAll(){return ordineRepository.findAll();}

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = ProdottoEsauritoException.class)
    public Ordine effettuaOrdine(String email) throws ProdottoEsauritoException, CarrelloVuotoException,UtenteInesistenteException, ProdottoInesistenteException{
        Utente u = utenteRepository.findByEmail(email);

        LinkedList<ProdottoInCarrello> prodotti=new LinkedList<>();
        Ordine ordine=new Ordine();

        for( ProdottoInCarrello pic: u.getCarrello()){
            int nuovaQnt = pic.getProdotto().getQnt()-pic.getQnt();
            if(nuovaQnt<0)
                //non posso effettuare l'acquisto poichè non ho abbastanza prodotti
                throw new ProdottoEsauritoException();
            pic.getProdotto().setQnt(nuovaQnt);

            //creo il prodotto che verrà mostrato nell'acquisto
            ProdottoInCarrello newpic = new ProdottoInCarrello(pic.getProdotto(), pic.getQnt(), pic.getPrezzo(), pic.getUtente());
            //associo l'ordine al prodotto
            newpic.setOrdine(ordine);
            //aggiungo il prodotto alla lista
            prodotti.add(newpic);
            //rimuovo il prodotto perchè è venduto
            prodottoInCarrelloRepository.delete(pic);
        }

        ordineRepository.save(ordine);
        entityManager.refresh(ordine);
        ordine.setAcquirente(u);
        ordine.setCarrello(prodotti);
        entityManager.merge(ordine);

        return ordine;
    }

}
