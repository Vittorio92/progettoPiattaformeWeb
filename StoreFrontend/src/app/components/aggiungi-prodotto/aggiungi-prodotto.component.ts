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
  form: any = {
    nome: null,
    squadra: null,
    prezzo: null,
    quantita: null,
    tipologia: null,
    link: null
  }

  ngOnInit(): void {
    this.mostra_opere()
  }

  crea(){
    

    if (this.form.nome == null || this.form.squadra == null || this.form.tipologia == null || this.form.prezzo<=0 || this.form.link == null || this.form.quantita<=0){
      alert("Non hai inserito tutti i campi correttamente !")
    }
    else if(this.form.tipologia !== 'maglia' && this.form.tipologia !== 'pantaloncino' && this.form.tipologia !== 'completo' && this.form.tipologia !== 'MAGLIA' && this.form.tipologia !== 'PANTALONCINO' && this.form.tipologia !== 'COMPLETO'){
      alert("Tipologia inserita non corretta !");
    }
    else{
    let p: Prodotto = new Prodotto();
    p.nome = this.form.nome;
    p.squadra = this.form.squadra;
    p.prezzo = this.form.prezzo
    p.qnt = this.form.quantita
    p.tipologia = this.form.tipologia.toUpperCase();
    p.immagine = this.form.link;
    
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
