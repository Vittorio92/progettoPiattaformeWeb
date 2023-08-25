package com.example.storebackend.Services;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Enum.Tipologia;
import com.example.storebackend.Repositories.ProdottoRepository;
import com.example.storebackend.Support.Exceptions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProdottoService {
    @Autowired
    private ProdottoRepository prodottoRepository;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Prodotto getProdotto(String nome, String squadra, Tipologia tipologia) throws ProdottoInesistenteException {
        if(!prodottoRepository.existsByNomeAndSquadraAndTipologia(nome,squadra,tipologia))
            throw new ProdottoInesistenteException();
        return prodottoRepository.findByNomeAndSquadraAndTipologia(nome,squadra,tipologia);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED) //richiede una nuova transazione
    public Prodotto registraProdotto(Prodotto p) throws ProdottoEsistenteException {
        if (prodottoRepository.existsById(p.getId())){
            throw new ProdottoEsistenteException();
        }
        return prodottoRepository.save(p);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Prodotto> getProdottoSquadra(String squadra) throws NessunProdottoException {
        if(prodottoRepository.findBySquadra(squadra).size()==0)
            throw new NessunProdottoException();
        return prodottoRepository.findBySquadra(squadra);
    }


}
