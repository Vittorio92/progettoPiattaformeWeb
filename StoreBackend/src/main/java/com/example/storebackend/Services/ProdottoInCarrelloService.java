package com.example.storebackend.Services;

import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Repositories.ProdottoInCarrelloRepository;
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

    @Transactional(readOnly = true, propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<ProdottoInCarrello> getAllProdottiInCarrello(){
        return prodottoInCarrelloRepository.findAll();
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Optional<ProdottoInCarrello> getProdottoInCarrello(int id){
        return prodottoInCarrelloRepository.findById(id);
    }
}
