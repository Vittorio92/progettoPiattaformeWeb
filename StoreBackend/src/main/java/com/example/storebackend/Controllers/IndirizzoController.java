package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Services.IndirizzoService;
import com.example.storebackend.Support.Exceptions.IndirizzoEsistenteException;
import com.example.storebackend.Support.Exceptions.IndirizzoInesistenteException;
import com.example.storebackend.Support.Exceptions.NessunIndirizzoException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
import com.example.storebackend.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RequestMapping("/indirizzo")
@RestController
@CrossOrigin
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @GetMapping("/get_indirizzi_utente")
    public ResponseEntity<List<Indirizzo>> getAllIndirizziUtente(@RequestParam(value = "id") int id){
        try {
            List<Indirizzo> risultato =indirizzoService.getAllIndirizziUtente(id);
            return new ResponseEntity<>(risultato, HttpStatus.OK);
        }catch (UtenteInesistenteException e){
            return new ResponseEntity(new Messaggio("L'utente indicato non esiste"), HttpStatus.BAD_REQUEST);
        }catch (NessunIndirizzoException e){
            return new ResponseEntity(new Messaggio("L'utente non ha nessun indirizzo salvato"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get")
    public ResponseEntity<Indirizzo> getIndirizzo(@RequestParam(value = "citta") String citta, @RequestParam(value = "via") String via, @RequestParam(value = "numeroC") int numeroC){
        try {
            Indirizzo risultato=indirizzoService.getIndirizzo(citta,via,numeroC);
            return new ResponseEntity<>(risultato, HttpStatus.OK);
        }catch (IndirizzoInesistenteException e){
            return new ResponseEntity(new Messaggio("L'indirizzo indicato non esiste"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/get_cap")
    public ResponseEntity<Indirizzo> getIndirizzoCAP(@RequestParam(value = "cap") int cap, @RequestParam(value = "via") String via, @RequestParam(value = "numeroC") int numeroC){
        try {
            Indirizzo risultato=indirizzoService.getIndirizzoCAP(cap,via,numeroC);
            return new ResponseEntity<>(risultato, HttpStatus.OK);
        }catch (IndirizzoInesistenteException e){
            return new ResponseEntity(new Messaggio("L'indirizzo indicato non esiste"), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/add")
    public ResponseEntity<Indirizzo> registraIndirizzo(@RequestBody @Valid Indirizzo indirizzo, @RequestParam(value = "id") int id){
        try {
            Indirizzo nuovo=indirizzoService.registraIndirizzo(indirizzo,id);
            return new ResponseEntity<>(nuovo,HttpStatus.OK);
        }catch (UtenteInesistenteException e){
            return new ResponseEntity(new Messaggio("L'utente indicato non esiste"), HttpStatus.BAD_REQUEST);
        }catch (IndirizzoEsistenteException e){
            return new ResponseEntity(new Messaggio("L'indirizzo indicato esiste già"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Indirizzo> aggiornaIndirizzo(@RequestBody @Valid Indirizzo indirizzo){
        try {
            Indirizzo aggiornato =indirizzoService.aggiornaIndirizzo(indirizzo);
            return new ResponseEntity<>(aggiornato,HttpStatus.OK);
        }catch (IndirizzoInesistenteException e){
            return new ResponseEntity(new Messaggio("L'indirizzo indicato non esiste"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messaggio> eliminaIndirizzo(@RequestParam(value = "id") int id){
        try {
            indirizzoService.eliminaIndirizzo(id);
            return new ResponseEntity<>(new Messaggio("Indirizzo eliminato con successo"), HttpStatus.OK);
        }catch (IndirizzoInesistenteException e){
            return new ResponseEntity<>(new Messaggio("L'indirizzo indicato non esiste"), HttpStatus.BAD_REQUEST);
        }
    }
}
