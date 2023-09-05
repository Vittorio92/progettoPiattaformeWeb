package com.example.storebackend.Controllers;

import com.example.storebackend.Entities.Prodotto;
import com.example.storebackend.Services.ProdottoService;
import com.example.storebackend.Support.Exceptions.ProdottoEsistenteException;
import com.example.storebackend.Support.Exceptions.ProdottoInesistenteException;
import com.example.storebackend.Support.Messaggio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/prodotto")
public class ProdottoController {

    @Autowired
    private ProdottoService prodottoService;


    @GetMapping("/get_all")
    public ResponseEntity<List<Prodotto>> getAll(){
        return new ResponseEntity<>(prodottoService.getAll(), HttpStatus.OK);
    }

    @GetMapping("/get_paginate")
    public ResponseEntity<List<Prodotto>> visualizzazionePaginata(@RequestParam(value = "pageNumber", defaultValue = "0") int pageNumber, @RequestParam(value = "pageSize", defaultValue = "5") int pageSize, @RequestParam(value = "ordinamento", defaultValue = "nome",required = false) String ordinamento) {
        List<Prodotto> result = prodottoService.visualizzazionePaginata(pageNumber,pageSize, ordinamento);
        if ( result.size() <= 0 ) {
            return new ResponseEntity(new Messaggio("Nessun risultato"), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Prodotto> registraProdotto(@RequestBody @Valid Prodotto prodotto) {
        try {
            Prodotto nuovo = prodottoService.registraProdotto(prodotto);
            return new ResponseEntity<>(nuovo, HttpStatus.OK);
        } catch (ProdottoEsistenteException e) {
            return new ResponseEntity(new Messaggio("Il prodotto è già presente nel sistema"), HttpStatus.BAD_REQUEST);
        }
    }

    @PutMapping("/update")
    public ResponseEntity<Prodotto> aggiornaProdotto(@RequestBody @Valid Prodotto prodotto){
        try{
            Prodotto aggiornato = prodottoService.aggiornaProdotto(prodotto);
            return new ResponseEntity<>(aggiornato, HttpStatus.OK);
        }catch (ProdottoInesistenteException e){
            return new ResponseEntity(new Messaggio("Il prodotto che si vuole aggiornare non è presente nel sistema"), HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<Messaggio> eliminaProdotto(@RequestParam(value = "id") int id) {
        try{
            prodottoService.eliminaProdotto(id);
            return new ResponseEntity<>(new Messaggio("Prodotto eliminato con successo"), HttpStatus.OK);
        }catch (ProdottoInesistenteException e){
            return new ResponseEntity<>(new Messaggio("Il prodotto indicato non è presente nel sistema"), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/ricerca_avanzata")
    public ResponseEntity<List<Prodotto>> ricercaAvanzata( @RequestParam(value = "nome",required = false) String nome, @RequestParam(value = "squadra", required = false) String squadra, @RequestParam(value = "tipologia", required = false) String tipologia){
        List<Prodotto> risultato = prodottoService.ricercaAvanzata(nome,squadra, tipologia);
        if(risultato.size()<=0)
            return new ResponseEntity("Nessun risultato", HttpStatus.OK);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }

    @GetMapping("/ricerca_nome")
    public ResponseEntity<List<Prodotto>> ricercaPerNome(@RequestParam(value = "nome")String nome){
        List<Prodotto> risultato = prodottoService.ricercaPerNome(nome);
        if(risultato.size()<=0)
            return new ResponseEntity("Nessun risultato", HttpStatus.OK);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }

    @GetMapping("/ricerca_squadra")
    public ResponseEntity<List<Prodotto>> ricercaPerSquadra(@RequestParam(value = "squadra") String squadra){
        List<Prodotto> risultato = prodottoService.ricercaPerSquadra(squadra);
        if(risultato.size()<=0)
            return new ResponseEntity("Nessun risultato", HttpStatus.OK);
        return new ResponseEntity<>(risultato, HttpStatus.OK);

    }

    @GetMapping("/ricerca_tipologia")
    public ResponseEntity<List<Prodotto>> ricercaPerTipologia(@RequestParam(value = "tipologia") String tipologia){
        List<Prodotto> risultato = prodottoService.ricercaPerTipologia(tipologia);
        if(risultato.size()<=0)
            return new ResponseEntity("Nessun risultato", HttpStatus.OK);
        return new ResponseEntity<>(risultato, HttpStatus.OK);
    }
}
