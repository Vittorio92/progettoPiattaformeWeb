import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from '../auth/auth.service';
import { Ordine } from '../models/ordine.models';


@Component({
  selector: 'app-acquisti',
  templateUrl: './acquisti.component.html',
  styleUrls: ['./acquisti.component.css']
})
export class AcquistiComponent implements OnInit {

  constructor(public http : HttpClient, public auth: AuthService) { }
  storicoAcquisti: Ordine[] = new Array();

  ngOnInit(): void {
    this.storico()
  }

  storico(): void{
    this.http.get<Ordine[]>("http://localhost:8081/ordine/getAcquisti?email="+this.auth.getEmail()).subscribe(ris =>{
      this.storicoAcquisti = ris;
    })
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
