package com.example.storebackend.Services;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Repositories.IndirizzoRepository;
import com.example.storebackend.Repositories.UtenteRepository;
import com.example.storebackend.Support.Exceptions.IndirizzoEsistenteException;
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
    public List<Indirizzo> getAllIndirizziUtente(String email) throws UtenteInesistenteException, NessunIndirizzoException {
        if(! utenteRepository.existsByEmail(email))
            throw new UtenteInesistenteException();
        Utente u = utenteRepository.findByEmail(email);
        List<Indirizzo> indirizzi= indirizzoRepository.findAllByUtenteAndAttivoTrue(u);
        if(indirizzi.size()<=0)
            throw new NessunIndirizzoException();
        return indirizzi;
    }

    @Transactional(readOnly = true,propagation = Propagation.SUPPORTS, isolation = Isolation.READ_COMMITTED)
    public List<Indirizzo> getIndirizzo(String citta, String via, int numeroC) throws IndirizzoInesistenteException {
        if(!indirizzoRepository.existsByCittaAndViaAndNumeroCivicoAndAttivoTrue(citta,via,numeroC))
            throw new IndirizzoInesistenteException();
        return indirizzoRepository.findByCittaAndViaAndNumeroCivico(citta,via,numeroC);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Indirizzo registraIndirizzo(Indirizzo indirizzo, int idUtente) throws UtenteInesistenteException, IndirizzoEsistenteException {
        //verifico l'esistenza dell'utente che registra l'indirizzo
        if(!utenteRepository.existsById(idUtente))
            throw new UtenteInesistenteException();
        Utente u=utenteRepository.findById(idUtente);

        //verifico l'unicità dell'indirizzo
        if(indirizzoRepository.existsByCapAndViaAndNumeroCivico(indirizzo.getCap(), indirizzo.getVia(), indirizzo.getNumeroCivico())){
            List<Indirizzo> esistenti = indirizzoRepository.findByCittaAndViaAndNumeroCivico(indirizzo.getCitta(), indirizzo.getVia(), indirizzo.getNumeroCivico());
            for(Indirizzo i: esistenti){
                if(i.getUtente().equals(u) && i.isAttivo())
                    throw new IndirizzoEsistenteException();
                else if (i.getUtente().equals(u)){
                    i.setAttivo(true);
                    indirizzoRepository.save(i);
                    return i;
                }
            }
        }

        Indirizzo nuovo=indirizzo;
        nuovo.setUtente(u);
        nuovo.setAttivo(true);

        //salvo il nuovo indirizzo
        indirizzoRepository.save(nuovo);
        return nuovo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public Indirizzo aggiornaIndirizzo(Indirizzo indirizzoAggiornato) throws IndirizzoInesistenteException{
        if(!indirizzoRepository.existsById(indirizzoAggiornato.getId()))
            throw new IndirizzoInesistenteException();

        Indirizzo nuovo= indirizzoRepository.findById(indirizzoAggiornato.getId());
        nuovo.setCap(indirizzoAggiornato.getCap());
        nuovo.setCitta(indirizzoAggiornato.getCitta());
        nuovo.setVia(indirizzoAggiornato.getVia());
        nuovo.setNumeroCivico(indirizzoAggiornato.getNumeroCivico());
        indirizzoRepository.save(nuovo);
        return nuovo;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.READ_COMMITTED)
    public void eliminaIndirizzo(int id) throws IndirizzoInesistenteException{
        if(!indirizzoRepository.existsById(id))
            throw new IndirizzoInesistenteException();
        Indirizzo i = indirizzoRepository.findById(id);
        i.setAttivo(false);
        indirizzoRepository.save(i);

    }
}
