package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Indirizzo;
import com.example.storebackend.Entities.Ordine;
import com.example.storebackend.Services.OrdineService;
import com.example.storebackend.Support.Exceptions.*;
import com.example.storebackend.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/ordine")
public class OrdineController {
    @Autowired
    OrdineService ordineService;

    @PostMapping("/add_ordine")
    public ResponseEntity<Ordine> effettuaOridne(@RequestParam(value = "email")String email, @RequestBody @Valid Indirizzo spedizione) {
        try {
            return new ResponseEntity<>(ordineService.effettuaOrdine(email, spedizione), HttpStatus.OK);
        } catch (CarrelloVuotoException e) {
            return new ResponseEntity(new Messaggio("Il carrello è vuoto."), HttpStatus.BAD_REQUEST);
        } catch (ProdottoEsauritoException e) {
            return new ResponseEntity(new Messaggio("La quantità che si vuole acquistare non è più disponibile."),HttpStatus.BAD_REQUEST);
        } catch (UtenteInesistenteException e) {
            return new ResponseEntity(new Messaggio("L'utente è inesistente."),HttpStatus.BAD_REQUEST);
        } catch (ProdottoInesistenteException e) {
            return new ResponseEntity(new Messaggio("Prodotto non più disponibile."),HttpStatus.BAD_REQUEST);
        }catch (PrezzoCambiatoException e){
            return new ResponseEntity(new Messaggio("Il prezzo del prodotto è cambiato."), HttpStatus.BAD_REQUEST);
        }
    }



    @GetMapping("/get_acquisti")
    public ResponseEntity<List<Ordine>> getOrdini(@RequestParam(value = "email") String email) {
        try {
            return new ResponseEntity<>(ordineService.getOrdiniUtente(email), HttpStatus.OK);
        } catch (UtenteInesistenteException e) {
            return new ResponseEntity(new Messaggio("Utente inesistente."), HttpStatus.BAD_REQUEST);
        }
    }


}
