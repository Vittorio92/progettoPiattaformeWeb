package com.example.storebackend.Services;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.IndirizzoRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.IndirizzoInesistenteException;
import com.example.storebackend.Support.Exceptions.NessunIndirizzoException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Indirizzo> getAllIndirizziUtente(int idUtente) throws UtenteInesistenteException, NessunIndirizzoException {
        if(! utenteRepository.existsById(idUtente))
            throw new UtenteInesistenteException();
        Utente u = utenteRepository.findById(idUtente);
        List<Indirizzo> indirizzi= indirizzoRepository.findAllByUtente(u);
        if(indirizzi.size()<=0)
            throw new NessunIndirizzoException();
        return indirizzi;
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Indirizzo getIndirizzo(String citta, String via, int numeroC) throws IndirizzoInesistenteException {
        if(!indirizzoRepository.existsByCittaAndViaAndNumeroCivico(citta,via,numeroC))
            throw new IndirizzoInesistenteException();
        return indirizzoRepository.findByCittaAndViaAndNumeroCivico(citta,via,numeroC);
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public Indirizzo getIndirizzoCAP(int cap, String via, int numeroC) throws IndirizzoInesistenteException{
        if(!indirizzoRepository.existsByCapAndViaAndNumeroCivico(cap,via,numeroC))
            throw new IndirizzoInesistenteException();
        return indirizzoRepository.findByCapAndViaAndNumeroCivico(cap,via,numeroC);
    }
}
