import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/auth/auth.service';
import { Indirizzo } from 'src/app/models/indirizzo.models';
import { Ordine } from 'src/app/models/ordine.models';
import { Utente } from 'src/app/models/utente.models';

@Component({
  selector: 'app-profilo',
  templateUrl: './profilo.component.html',
  styleUrls: ['./profilo.component.css']
})
export class ProfiloComponent implements OnInit {

  utente: Utente = new Utente();
  indirizzi: Indirizzo[] = new Array(); 
  aggiungi: boolean = false;
  form: any ={
    via: null,
    citta: null,
    numeroCivico: null,
    cap: null
  }
  storico: boolean = false;
  storicoAcquisti: Ordine[] = new Array();

  constructor(private http: HttpClient, private auth: AuthService) {}


  ngOnInit(): void {
    this.getUtente();
    this.getIndirizzi();
  }

  getUtente(){
    this.http.get<Utente>("http://localhost:8081/utente/ricerca_utente_email?email="+this.auth.getEmail()).subscribe(ris => {
      this.utente=ris;
    });
  }

  getIndirizzi(){
    this.http.get<Indirizzo[]>("http://localhost:8081/indirizzo/get_indirizzi_utente?email="+this.auth.getEmail()).subscribe(ris => {
      this.indirizzi=ris;
    });
  }

  eliminaI(indirizzo: Indirizzo): void{
    this.http.delete<Indirizzo>("http://localhost:8081/indirizzo/delete?id="+indirizzo.id).subscribe(ris => {
      alert("Indirizzo eliminato correttamente");
      this.ngOnInit();
    });
  }

  addIndirizzo(): void {
    this.aggiungi=!this.aggiungi;
  }

  registra(): void {
    this.http.post<Indirizzo>("http://localhost:8081/indirizzo/add?id="+this.utente.id, this.form).subscribe({
      next: (ris: Indirizzo)  => {
        alert("Indirizzo registrato correttamente");
        this.ngOnInit();
      },
      error: (err: any) => {
        alert("Indirizzo già registrato");
      }
    });
  }

  numberOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }

  mostra_storico(): void {
    this.storico = !this.storico;
  }

  esci() : void{
    this.storico = false;
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
