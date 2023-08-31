import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { ProdottoInCarrello } from 'src/app/models/prodottoInCarrello.models';
import { Ordine } from 'src/app/models/ordine.models';

@Component({
  selector: 'app-carrello',
  templateUrl: './carrello.component.html',
  styleUrls: ['./carrello.component.css']
})
export class CarrelloComponent implements OnInit {

  constructor(public http: HttpClient) {
    for(let i = 0; i< this.prodotti.length; i++){
      this.num[i] = 0;
    }
   }

  ngOnInit(): void {
    this.mostraCarrello()
    this.calcolaPrezzo()
  }

  prodotti: ProdottoInCarrello[]= new Array();
  num: number[] = new Array();
  storicoAcquisti : boolean = false;

  

  diminuisci(indice: number): void{
    
  }

  elimina(indice: number): void{
    
  }
  mostraCarrello(): void{
    
  }

  calcolaPrezzo(): number{
    let costo: number = 0;
    
    return costo;
    
  }

  acquista():void{
  }
  

  storico(): void{
    this.storicoAcquisti = !this.storicoAcquisti
  }
 


}
