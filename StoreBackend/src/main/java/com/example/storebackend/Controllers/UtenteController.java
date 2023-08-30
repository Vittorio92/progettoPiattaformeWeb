package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Utente;
import com.example.storebackend.Services.UtenteService;
import com.example.storebackend.Support.Exceptions.OrdineInesistenteException;
import com.example.storebackend.Support.Exceptions.UtenteEsistenteException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
import com.example.storebackend.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@CrossOrigin
@RestController
@RequestMapping("/utente")
public class UtenteController {
    @Autowired
    UtenteService utenteService;

    @GetMapping("/ricerca_utente_email")
    public ResponseEntity<Utente> ricercaUtente(@RequestParam(value="email") String email){
        try {
            Utente ris = utenteService.getUtente(email);
            return new ResponseEntity<>(ris, HttpStatus.OK);
        }catch (UtenteInesistenteException e){
            return new ResponseEntity(new Messaggio("L'utente indicato non esiste"), HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/tutti_gli_utenti")
    public ResponseEntity<List<Utente>> tuttiGliUtenti(){
        List<Utente> ris = utenteService.getAllUtenti();
        return new ResponseEntity<>(ris, HttpStatus.OK);
    }

    @PostMapping("/crea_utente")
    public ResponseEntity<?> registraUtente(@RequestBody @Valid Utente utente){
        try{
            Utente nuovo= utenteService.registraUtente(utente);
            return new ResponseEntity<>(nuovo, HttpStatus.OK);
        }catch(UtenteEsistenteException e){
            return new ResponseEntity<>(new Messaggio("Utente già registrato!"),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/elimina_utente")
    public ResponseEntity<Messaggio> eliminaUtente(@RequestParam(value="id_utente") int id){
        try{
            utenteService.eliminaUtente(id);
            return new ResponseEntity<>(new Messaggio("Utente eliminato con successo!"),HttpStatus.OK);
        }catch(UtenteInesistenteException e){
            e.printStackTrace();;
        }catch(OrdineInesistenteException e){
            return new ResponseEntity<>(new Messaggio("Ordine inesistente!"),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Messaggio("Utente inesistente!"),HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/ricerca_avanzata")
    public ResponseEntity<List<Utente>> ricercaAvanzata(@RequestParam(value = "nome", required = false)String nome,@RequestParam(value = "cognome", required = false)String cognome,  @RequestParam(value = "dataDiNascita", required = false)String dataDiNascita,@RequestParam(value = "email", required = false) String email){
        List<Utente> risultato = utenteService.ricercaAvanzata(nome, cognome, dataDiNascita, email);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }
}
