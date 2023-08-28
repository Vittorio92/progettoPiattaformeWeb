package com.example.storebackend.Services;

import com.example.storebackend.Entities.*;
import com.example.storebackend.Repositories.*;
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
    private ProdottoRepository prodottoRepository;

    @Autowired
    private IndirizzoRepository indirizzoRepository;

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

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = {ProdottoEsauritoException.class, PrezzoCambiatoException.class } )
    public Ordine effettuaOrdine(String email, Indirizzo indirizzoSpedizione) throws ProdottoEsauritoException, CarrelloVuotoException,UtenteInesistenteException, ProdottoInesistenteException, PrezzoCambiatoException{
        if(!utenteRepository.existsByEmail(email))
            throw new UtenteInesistenteException();
        Utente u = utenteRepository.findByEmail(email);

        Indirizzo spedizione=indirizzoSpedizione;
        boolean esiste=false;
        List<Indirizzo> indirizziUtente = indirizzoRepository.findAllByUtente(u);
        for(Indirizzo i: indirizziUtente)
            if(i.equals(indirizzoSpedizione)) {
                spedizione=i;
                esiste = true;
                break;
            }

        if(!esiste){
            spedizione.setUtente(u);
            indirizzoRepository.save(spedizione);
        }

        LinkedList<ProdottoInCarrello> prodotti=new LinkedList<>();
        Ordine ordine=new Ordine();

        List<ProdottoInCarrello> carrelloProdottiOrdine=u.getCarrello();
        if(carrelloProdottiOrdine.size()<=0)
            throw new CarrelloVuotoException();

        for( ProdottoInCarrello pic: carrelloProdottiOrdine){
            if(!prodottoRepository.existsById(pic.getProdotto().getId()))
                throw new ProdottoInesistenteException();
            int nuovaQnt = pic.getProdotto().getQnt()-pic.getQnt();
            if(nuovaQnt<0)
                //non posso effettuare l'acquisto poichè non ho abbastanza prodotti
                throw new ProdottoEsauritoException();
            pic.getProdotto().setQnt(nuovaQnt);
            if(pic.getPrezzo() != pic.getProdotto().getPrezzo())
                throw new PrezzoCambiatoException();


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
        ordine.setData(new Date(System.currentTimeMillis()));
        ordine.setCarrello(prodotti);
        ordine.setSpedizione(spedizione);
        entityManager.merge(ordine);

        return ordine;
    }

}
