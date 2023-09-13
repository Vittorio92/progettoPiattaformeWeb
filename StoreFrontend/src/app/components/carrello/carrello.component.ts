import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { ProdottoInCarrello } from 'src/app/models/prodottoInCarrello.models';
import { Ordine } from 'src/app/models/ordine.models';
import { Indirizzo } from 'src/app/models/indirizzo.models';

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent implements OnInit {

  constructor(public http: HttpClient, public auth: AuthService) {
    for(let i = 0; i< this.prodotti.length; i++){
      this.num[i] = 0;
    }
  }

  ngOnInit(): void {
    this.mostraCarrello()
    this.calcolaPrezzo()
    this.getIndirizzi();
  }

  prodotti: ProdottoInCarrello[]= new Array();
  num: number[] = new Array();
  storicoAcquisti : boolean = false;
  list: Indirizzo[] =new Array();
  form: any ={
    spedizione: null
  }

  getIndirizzi(): void{
    this.http.get<Indirizzo[]>("http://localhost:8081/indirizzo/get_indirizzi_utente?email="+this.auth.getEmail()).subscribe(ris => {
      this.list=ris;
    });
  }
  

  diminuisci(indice: number): void{
    this.http.delete("http://localhost:8081/carrello/elimina?email="+this.auth.getEmail()+"&codice="+this.prodotti[indice].id).subscribe(ris =>{
      this.ngOnInit()
    })
  }

  elimina(indice: number): void{
    this.http.delete("http://localhost:8081/carrello/elimina_tutto?email="+this.auth.getEmail()+"&codice="+this.prodotti[indice].id).subscribe(ris =>{
      this.ngOnInit()
    })
  }
  mostraCarrello(): void{
    this.http.get<ProdottoInCarrello[]>("http://localhost:8081/carrello/get_carrello?email="+this.auth.getEmail()).subscribe(ris =>{
      this.prodotti = ris;
    })
  }

  calcolaPrezzo(): number{
    let costo: number = 0;
    for(let i = 0; i<this.prodotti.length; i++){
      costo = costo + (this.prodotti[i].prezzo * this.prodotti[i].qnt)
    }
    return costo;
    
  }

  acquista():void{
    if(this.prodotti.length == 0){
      alert("Il carrello è vuoto")
    }
    else{
      let ind: Indirizzo = new Indirizzo();
      for(let i = 0; i < this.list.length; i++){
        if(this.list[i].id == this.form.spedizione){
          ind=this.list[i];
        }
      }
    this.http.post<Ordine>("http://localhost:8081/ordine/add_ordine?email="+this.auth.getEmail(), ind).subscribe({
      error: (err: any) => {
        const errore = err.error.messaggio;
        alert("Impossibile effettuare l'acquisto: "+ errore );
      },
      next: (ris: Ordine) => {
        alert("Acquisto effettuato con successo")
        this.prodotti = new Array();
      }
    });
    }
  }

  storico(): void{
    this.storicoAcquisti = !this.storicoAcquisti
  }



}

