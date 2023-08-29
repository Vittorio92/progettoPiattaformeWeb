package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Entities.ProdottoInCarrello;
import com.example.storebackend.Services.ProdottoInCarrelloService;
import com.example.storebackend.Services.ProdottoService;
import com.example.storebackend.Services.UtenteService;
import com.example.storebackend.Support.Exceptions.ProdottoEsauritoException;
import com.example.storebackend.Support.Exceptions.ProdottoInesistenteException;
import com.example.storebackend.Support.Exceptions.UtenteInesistenteException;
import com.example.storebackend.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("(/carrello")
public class ProdottoInCarrelloController {
    @Autowired
    private ProdottoInCarrelloService prodottoInCarrelloService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private ProdottoService prodottoService;

    @GetMapping("/get_carrello")
    public ResponseEntity<?> getCarrello(@RequestParam(value="email",required=true) String email){
        try{
            List<ProdottoInCarrello> ris = prodottoInCarrelloService.getCarrello(email);
            return new ResponseEntity<>(ris, HttpStatus.OK);
        }catch(UtenteInesistenteException e){
            return new ResponseEntity<>(new Messaggio("Utente inesistente!"),HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/elimina")
    public ResponseEntity<Messaggio> eliminaDaCarrello(@RequestParam(value="email",required=true)String email, @RequestParam(value="codice",required = true) int codice){
        try{
            List<ProdottoInCarrello> lista = prodottoInCarrelloService.getCarrello(email);
            for(ProdottoInCarrello pic:lista)
                if(pic.getId()==codice){
                    prodottoInCarrelloService.diminuisciQuantitaProdottoInCarrello(email,pic);
                    return new ResponseEntity<>(new Messaggio("Prodotto eliminata dal carrello."),HttpStatus.OK);
                }
        } catch (UtenteInesistenteException e) {
            return new ResponseEntity<>(new Messaggio("Utente Inesistente"), HttpStatus.BAD_REQUEST);
        } catch (ProdottoInesistenteException e) {
            return new ResponseEntity<>(new Messaggio("Prodotto Inesistente"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Messaggio("Prodotto nel carrello non eliminata."), HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/elimina_tutto")
    public ResponseEntity<Messaggio> eleiminatutto(@RequestParam(value="email",required = true) String email,@RequestParam(value="codice",required = true) int codice){
        try {
            List<ProdottoInCarrello> lista = prodottoInCarrelloService.getCarrello(email);
            for(ProdottoInCarrello pic : lista){
                if(pic.getId()==codice){
                   prodottoInCarrelloService.rimuoviProdottoInCarrello(pic);
                    return new ResponseEntity<>(new Messaggio("Prodotto eliminata dal carrello."), HttpStatus.OK);
                }
            }
        } catch (UtenteInesistenteException e) {
            return new ResponseEntity<>(new Messaggio("Utente Inesistente"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Messaggio("Prodotto nel carrello non eliminato"),HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/add_prodotto")
    public ResponseEntity<Messaggio> addProdotto(@RequestParam(value="email") String email, @RequestParam(value="codice") int codice, @RequestParam(value="qnt") int qnt){
        try{
            Prodotto p = prodottoService.getProdottoById(codice);
           prodottoInCarrelloService.add(email,p,qnt);
        }catch (UtenteInesistenteException e) {
            return new ResponseEntity(new Messaggio("Utente non trovato."),HttpStatus.BAD_REQUEST);
        } catch (ProdottoInesistenteException e) {
            return new ResponseEntity(new Messaggio("Prodotto inesistente."),HttpStatus.BAD_REQUEST);
        } catch (ProdottoEsauritoException e) {
            return new ResponseEntity(new Messaggio(" Quantità non disponibile."),HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new Messaggio("Prodotto aggiunto al carrello!"), HttpStatus.OK);
    }
}
