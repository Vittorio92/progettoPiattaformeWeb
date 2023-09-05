import { HttpClient } from '@angular/common/http';
import { Component, Input, OnInit } from '@angular/core';
import { Prodotto } from 'src/app/models/prodotto.models';
import { Ordine } from 'src/app/models/ordine.models';
import { Utente } from 'src/app/models/utente.models';


@Component({
  selector: 'app-admin-page',
  templateUrl: './admin-page.component.html',
  styleUrls: ['./admin-page.component.css']
})
export class AdminPageComponent implements OnInit {

  modifica: boolean[] = new Array();
  eliminare: boolean[] = new Array();
  eliminare_user: boolean[] = new Array();
  modifica_prezzi: boolean[] = new Array();
  modifica_quantita: boolean[] = new Array();
  storico: boolean[] = new Array();
  storicoAcquisti: Ordine[] = new Array();


  constructor(public http: HttpClient) {
    for (let i = 0; i < this.prodotti.length; i++) {
      this.modifica[i] = false;
      this.eliminare[i] = false;
      this.modifica_prezzi[i] = false;
      this.modifica_quantita[i]=false;
    }
    for (let i = 0; i < this.utenti.length; i++) {
      this.eliminare_user[i] = false;
      this.storico[i] = false;
    }
  }




  ngOnInit(): void {
    this.mostra_prodotti();
    this.mostra_utenti();
  }

  prodotti: Prodotto[] = new Array();
  utenti: Utente[] = new Array();
  scheda: boolean = false;
  

  mostra_prodotti(): void {
    this.http.get<Prodotto[]>("http://localhost:8081/prodotto/get_all").subscribe(ris => {
      this.prodotti = ris
    });
  }

  mostra_utenti(): void {
    this.http.get<Utente[]>('http://localhost:8081/utente/tutti_gli_utenti').subscribe(ris => {
      this.utenti = ris;
    })
  }

  modifica_prodotto(indice: number): void {
    this.modifica[indice] = !this.modifica[indice]
    this.eliminare[indice] = false
    this.modifica_quantita[indice]=false;
    this.modifica_prezzi[indice] = false;
  }

  conferma_prezzo(indice: number, prezzo: string): void {
    if (parseInt(prezzo) <= 0) {
      alert("Attenzione prezzo inserito non valido!");
    }
    else {
      this.prodotti[indice].prezzo = parseInt(prezzo);
      this.http.put<Prodotto>('http://localhost:8081/prodotto/update', this.prodotti[indice]).subscribe(ris => {
        this.prodotti[indice] = ris;
        this.ngOnInit();
      })
    }

  }

  conferma_quantita(indice: number, quantita: string): void {
    if (parseInt(quantita) < 0) {
      alert("Attenzione quantità inserita non valida!");
    }
    else {
      this.prodotti[indice].qnt = parseInt(quantita)
      this.http.put<Prodotto>('http://localhost:8081/prodotto/update', this.prodotti[indice]).subscribe(ris => {
        this.prodotti[indice] = ris
        this.ngOnInit();
      })
    }

  }

  elimina_prodotto(indice: number): void {

    this.eliminare[indice] = !this.eliminare[indice]
    this.modifica[indice] = false

  }

  elimina(id: Number): void {
    this.http.delete('http://localhost:8081/prodotto/delete?id=' + id).subscribe(ris => {
      alert("Prodotto Eliminato!")
      this.ngOnInit();
    })
  }

  non_eliminare(indice: number) {
    this.eliminare[indice] = false;
  }

  prezzo(indice: number): void{

    this.modifica_prezzi[indice] = !this.modifica_prezzi[indice];
    this.modifica_quantita[indice] = false;
    this.modifica[indice] = false;

  }

  quantita(indice: number): void{

    this.modifica_quantita[indice] = !this.modifica_quantita[indice]
    this.modifica_prezzi[indice] = false;
    this.modifica[indice] = false;

  }

  //aggiungi prodotto
  aggiungi(): void{
    for (let i = 0; i < this.prodotti.length; i++) {
      this.modifica[i] = false;
      this.eliminare[i] = false;
      this.modifica_prezzi[i] = false;
      this.modifica_quantita[i]=false;
    }
    this.scheda=!this.scheda;
    
  }

  mostra_storico(indice: number, email: string): void{
    this.http.get<Ordine[]>("http://localhost:8081/ordine/get_acquisti?email="+this.utenti[indice].email).subscribe(ris =>{
      this.storico[indice] = true;
      for(let i = 0; i < this.storico.length; i++){
        if( i != indice){
          this.storico[i] = false;
        }
      }
      this.storicoAcquisti = ris;
    })
  }

  esci(indice: number) : void{
    this.storico[indice] = false;
  }

  calcolaPrezzo(indice: number): number{
    let costo: number = 0;
    let carrello = this.storicoAcquisti[indice].carrello;

    for(let i = 0; i < carrello.length; i++){
      costo += carrello[i].prezzo*carrello[i].qnt
    }

    return costo;
    
  }

}

