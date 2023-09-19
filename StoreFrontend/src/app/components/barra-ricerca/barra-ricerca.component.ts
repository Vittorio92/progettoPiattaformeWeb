import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Prodotto } from 'src/app/models/prodotto.models';
import { ProdottoInCarrello } from 'src/app/models/prodottoInCarrello.models';

@Component({
  selector: 'app-barra-ricerca',
  templateUrl: './barra-ricerca.component.html',
  styleUrls: ['./barra-ricerca.component.css']
})
export class BarraRicercaComponent implements OnInit {

  num: number[] = new Array();
  prodotto : Prodotto = new Prodotto()
  risultato: Prodotto[] = new Array()
  tipologia: string = 'TUTTE';

  constructor(public http: HttpClient, public auth: AuthService) {
    this.prodotto.tipologia = "";
  }

  ngOnInit(): void {
  
  }

cerca(nome: string, squadra: string, tipo: string){
    let link = "?"
    if(nome!=null && nome.length!=0){link+="nome="+nome}
    if(squadra!=null && squadra.length!=0){link+="&squadra="+squadra}
    if(tipo!=null && tipo.length!=0 && tipo!="Tutte"){link+="&tipologia="+tipo}
    this.http.get<Prodotto[]>("http://localhost:8081/prodotto/ricerca_avanzata"+link).subscribe(ris =>{
      this.risultato = ris;
      for(let i = 0; i<ris.length; i++){
        this.num[i] = 0;
      }
      if(this.risultato.length == 0){
        alert("La ricerca non ha portato ad alcun risultato")
      }
    })
  }

  aumenta(p: Prodotto): void{
    for(let i = 0; i< this.risultato.length; i++){
      if(p.id == this.risultato[i].id){
        if(this.num[i]<this.risultato[i].qnt){
          this.num[i] ++;
        }
        else{
          alert("Non sono più disponibili altri pezzi di questo prodotto!")
        }
      }
    }
  }

  diminuisci(p: Prodotto): void{
    for(let i = 0; i< this.risultato.length; i++){
      if(p.id == this.risultato[i].id){
        if(this.num[i]!=0)
          this.num[i] --;
      }
    }
  }

  aggiungiAlCarrello(idProdotto: number, quantita: number): void{
    if(!this.auth.isauthenticated()){
      alert("Effettua il log-in per aggiungere il prodotto al carrello!")
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
