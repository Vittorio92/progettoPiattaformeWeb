package com.example.storebackend.Services;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Enum.Tipologia;
import com.example.storebackend.Repositories.ProdottoInCarrelloRepository;
import com.example.storebackend.Repositories.ProdottoRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedList;
import java.util.List;

@Service
public class ProdottoService {
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private ProdottoInCarrelloRepository prodottoInCarrelloRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Prodotto getProdotto(String nome, String squadra, Tipologia tipologia) throws ProdottoInesistenteException {
        if(!prodottoRepository.existsByNomeAndSquadraAndTipologia(nome,squadra,tipologia))
            throw new ProdottoInesistenteException();
        return prodottoRepository.findByNomeAndSquadraAndTipologia(nome,squadra,tipologia);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Prodotto getProdottoById(int id) throws ProdottoInesistenteException{
        if(!prodottoRepository.existsById(id))
            throw new ProdottoInesistenteException();
        Prodotto prodotto = prodottoRepository.findById(id);
        return prodotto;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Prodotto registraProdotto(Prodotto p) throws ProdottoEsistenteException {
        if (prodottoRepository.existsByNomeAndSquadraAndTipologia(p.getNome(), p.getSquadra(),p.getTipologia())){
            throw new ProdottoEsistenteException();
        }
        Prodotto nuovo = prodottoRepository.save(p);
        entityManager.lock(nuovo, LockModeType.OPTIMISTIC_FORCE_INCREMENT);
        entityManager.refresh(nuovo);
        return nuovo;
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> ricercaPerSquadra(String squadra)  {
        List<Prodotto> risultato=prodottoRepository.findBySquadra(squadra);
        return risultato;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Prodotto aggiornaProdotto(Prodotto prodottoAggiornato) throws ProdottoInesistenteException {
        if(!prodottoRepository.existsByNome(prodottoAggiornato.getNome())){
            throw new ProdottoInesistenteException();
        }
        Prodotto p = prodottoRepository.findById(prodottoAggiornato.getId());
        p.setPrezzo(prodottoAggiornato.getPrezzo());
        p.setQnt(prodottoAggiornato.getQnt());
        return prodottoRepository.save(p);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaProdotto(int id) throws ProdottoInesistenteException {
        if(!prodottoRepository.existsById(id)){
            throw new ProdottoInesistenteException();
        }
        Prodotto prodotto = prodottoRepository.findById(id);

        //elimino il prodotto dal carrello degli utenti
        List<Utente> utenti = utenteRepository.findAll();
        for(Utente u: utenti){
            List<ProdottoInCarrello> carrello = u.getCarrello();
            for(ProdottoInCarrello pic: carrello){
                if(prodotto.getNome().equals(pic.getProdotto().getNome()) && prodotto.getSquadra().equals(pic.getProdotto().getSquadra()) && prodotto.getTipologia().equals(pic.getProdotto().getTipologia())){
                    prodottoInCarrelloRepository.deleteById(pic.getId());
                }
            }
        }

        entityManager.lock(prodotto, LockModeType.OPTIMISTIC_FORCE_INCREMENT);

        //per evitare eventuali problemi di chiave esterna lo elimino da eventuali carrelli rimasti
        List<ProdottoInCarrello> carrrelliAssociati = prodotto.getProdottiNelCarrello();

        for(ProdottoInCarrello pic : carrrelliAssociati){
            prodottoInCarrelloRepository.delete(pic);
        }

        //elimino il prodotto
        prodottoRepository.deleteById(id);
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> visualizzazionePaginata(int pageNumber, int pageSize, String ordinamento){
        Pageable pageable = PageRequest.of(pageNumber, pageSize, Sort.by(ordinamento));
        Page<Prodotto> pagedResult = prodottoRepository.findAll(pageable);
        if(!pagedResult.hasContent())
            return new LinkedList<>();

        return pagedResult.getContent();
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> ricercaAvanzata(String nome, String squadra, String tipologia){

        Tipologia t=null;
        try {
            t=Tipologia.valueOf(tipologia);
        }catch (IllegalArgumentException iae){

        }

        List<Prodotto> risultato = prodottoRepository.ricercaAvanzata(nome, squadra, t);
        return risultato;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> ricercaPerNome(String nome){
        List<Prodotto> risultato = prodottoRepository.findByNomeContaining(nome);
        return risultato;
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> ricercaPerTipologia(String t){
        Tipologia tipologia;
        try {
            tipologia=Tipologia.valueOf(t);
            List<Prodotto> risultato=prodottoRepository.findByTipologia(tipologia);
            return risultato;
        }catch (IllegalArgumentException iae){
            return new LinkedList<>();
        }
    }

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> getAll(){
        return prodottoRepository.findAll();
    }



}
