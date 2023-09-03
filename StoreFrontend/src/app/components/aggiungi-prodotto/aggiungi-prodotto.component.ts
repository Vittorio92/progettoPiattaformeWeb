import { HttpClient } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { Prodotto } from 'src/app/models/prodotto.models';

@Component({
  selector: 'app-aggiungi-prodotto',
  templateUrl: './aggiungi-prodotto.component.html',
  styleUrls: ['./aggiungi-prodotto.component.css']
})
export class AggiungiProdottoComponent implements OnInit {

  constructor(private http: HttpClient) { }

  prodotti : Prodotto[] = new Array();

  ngOnInit(): void {
    this.mostra_opere()
  }

  crea(nome: string, squadra: string, prezzo: string, quantita: string, tipologia: string, link:string){
    

    if (nome == null || squadra == null || prezzo == null || parseInt(prezzo)<=0 || quantita == null || parseInt(quantita)<=0){
      alert("Non hai inserito tutti i campi correttamente !")
    }
    else{
    let p: Prodotto = new Prodotto();
    p.nome = nome;
    p.squadra = squadra
    p.prezzo = parseInt(prezzo);
    p.qnt = parseInt(quantita);
    p.tipologia = tipologia;
    p.immagine = link;

    
    this.http.post<Prodotto>("http://localhost:8081/prodotto/add", p ).subscribe({
      error: (err: any) => {
        alert("Prodotto già presente nello store!");
      },
      next: (ris: Prodotto) => {
        alert("Prodotto aggiunto correttamente allo store!");
        window.location.reload();
      }
    });
    }
  }

  mostra_opere(): void{
    this.http.get<Prodotto[]>("http://localhost:8081/prodotto/get_all").subscribe(ris => {
      this.prodotti = ris
    });
  }

  numberOnly(event: any): boolean {
    const charCode = (event.which) ? event.which : event.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
      return false;
    }
    return true;
  }
}
