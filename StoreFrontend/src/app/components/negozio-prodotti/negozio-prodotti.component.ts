import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Prodotto } from 'src/app/models/prodotto.models';
import { ProdottoInCarrello } from 'src/app/models/prodottoInCarrello.models';

@Component({
  selector: 'app-negozio-prodotti',
  templateUrl: './negozio-prodotti.component.html',
  styleUrls: ['./negozio-prodotti.component.css']
})
export class NegozioProdottiComponent implements OnInit {

  mostra: boolean = false;
  tipologia: boolean = true;
  prodotti: Prodotto[]= new Array();
  num: number[] = new Array();

  constructor(public http: HttpClient, public auth: AuthService) { }

  ngOnInit(): void {
    this.mostra_opere()
  }

  mostra_opere(): void{
   this.http.get<Prodotto[]>("http://localhost:8081/prodotto/get_all").subscribe(ris => {
      this.prodotti = ris
      for(let i = 0; i< this.prodotti.length; i++){
        this.num[i] = 0;
      }
    });
    console.log(this.prodotti.length)
  }

  aumenta(p: Prodotto): void{
    for(let i = 0; i< this.prodotti.length; i++){
      if(p.id == this.prodotti[i].id){
        if(this.num[i]<this.prodotti[i].qnt){
          this.num[i] ++;
        }
        else{
          alert("Non sono più disponibili altri pezzi!")
        }
      }
    }
  }

  diminuisci(p: Prodotto): void{
    for(let i = 0; i< this.prodotti.length; i++){
      if(p.id == this.prodotti[i].id){
        if(this.num[i]!=0)
          this.num[i] --;
      }
    }
  }

  aggiungiAlCarrello(idProdotto: number, quantita: number): void{
    if(!this.auth.isauthenticated()){
      alert("Effettuare il log-in per aggiungere il prodotto al carrello!")
    }
    else if(quantita == 0){
      alert("Quantità selezionata non valida!")
    }
    else{
      let mail = this.auth.getEmail()
      this.http.post<ProdottoInCarrello>("http://localhost:8081/carrello/add_prodotto?email="+mail+"&codice="+idProdotto+"&qnt="+quantita, null).subscribe(ris =>{
        alert("Prodotto aggiunto al carrello correttamente !")
      });
    }
  }
}
